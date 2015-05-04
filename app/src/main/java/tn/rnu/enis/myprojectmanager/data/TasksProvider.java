package tn.rnu.enis.myprojectmanager.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Mohamed on 27/04/2015.
 */
public class TasksProvider extends ContentProvider {

    private TasksDb mTaskDb;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int PROJECT = 100;
    static final int PROJECT_WITH_ID = 101;
    static final int TASK = 102;
    static final int TASK_WITH_ID = 103;
    static final int TASK_WITH_PROJECT = 104;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.PATH_PROJECTS, PROJECT);
        matcher.addURI(authority, Contract.PATH_PROJECTS + "/#", PROJECT_WITH_ID);

        matcher.addURI(authority, Contract.PATH_TASKS, TASK);
        matcher.addURI(authority, Contract.PATH_TASKS + "/#", TASK_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mTaskDb = new TasksDb(getContext());
        return (mTaskDb !=null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case PROJECT: {
                retCursor = mTaskDb.getReadableDatabase().query(
                        Contract.Project.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TASK: {
                retCursor = mTaskDb.getReadableDatabase().query(
                        Contract.Task.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PROJECT_WITH_ID:
            {
                retCursor = getProjectById(uri, projection, sortOrder);
                break;
            }
            case TASK_WITH_ID:
            {
                Log.i("lll","TASK with id");
                retCursor = getTaskById(uri, projection, sortOrder);

                break;
            }
            case TASK_WITH_PROJECT:
            {
                retCursor = getTasksWithProjectID(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PROJECT:
                return Contract.Project.CONTENT_TYPE;
            case PROJECT_WITH_ID:
                return Contract.Project.PROJECTS_ITEM_TYPE;
            case TASK:
                return Contract.Task.CONTENT_TYPE;
            case TASK_WITH_ID:
                return Contract.Task.TASKS_ITEM_TYPE;
            case TASK_WITH_PROJECT:
                return Contract.Task.TASKS_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mTaskDb.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PROJECT: {
                long _id = db.insert(Contract.Project.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.Project.buildProjectUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TASK: {
                long _id = db.insert(Contract.Task.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.Task.buildTASKUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String projection, String[] args) {
        final SQLiteDatabase db = mTaskDb.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted = 0 ;
        if ( null == projection ) projection = "1";
        switch (match) {
            case PROJECT: {
                rowsDeleted = db.delete(Contract.Project.TABLE_NAME, projection, args);
                break;
            }
            case TASK: {
                rowsDeleted = db.delete(Contract.Task.TABLE_NAME, projection, args);
                break;
            }
        };
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String projection, String[] args) {
        final SQLiteDatabase db = mTaskDb.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PROJECT:
                rowsUpdated = db.update(Contract.Project.TABLE_NAME, values, projection,
                        args);
                break;
            case TASK:
                rowsUpdated = db.update(Contract.Task.TABLE_NAME, values, projection,
                        args);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private static SQLiteQueryBuilder sGetTaskByProjectId ;

    static{
        sGetTaskByProjectId = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON Project._id = Task.project
        sGetTaskByProjectId.setTables(
                Contract.Project.TABLE_NAME + " INNER JOIN " +
                        Contract.Task.TABLE_NAME +
                        " ON " + Contract.Project.TABLE_NAME +
                        "." + Contract.Project._ID +
                        " = " + Contract.Task.TABLE_NAME +
                        "." + Contract.Task.TASK_PROJECT);
    }


    //Project._ID = ?
    private static final String sProjectById =
            Contract.Project.TABLE_NAME +
                    "." + Contract.Project._ID + " = ? ";

    private Cursor getTasksWithProjectID(Uri uri, String[] projection, String sortOrder) {
        String id = Contract.getIDFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sProjectById;
        selectionArgs = new String[]{id};

        return sGetTaskByProjectId.query(mTaskDb.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTaskById(Uri uri, String[] projection, String sortOrder) {
        return mTaskDb.getWritableDatabase().query(Contract.Task.TABLE_NAME,projection,Contract.Task._ID+"= ?",new String[]{Contract.getIDFromUri(uri)},null,null,sortOrder);

    }

    private Cursor getProjectById(Uri uri, String[] projection, String sortOrder) {
        return mTaskDb.getWritableDatabase().query(Contract.Project.TABLE_NAME,projection,Contract.Project._ID+"= ?",new String[]{Contract.getIDFromUri(uri)},null,null,sortOrder);
    }
}
