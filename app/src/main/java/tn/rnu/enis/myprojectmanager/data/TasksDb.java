package tn.rnu.enis.myprojectmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed on 27/04/2015.
 */
public class TasksDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "project_manager.sql";
    public static final int VERSION = 1 ;

    public TasksDb(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String req_project = "create table "+ Contract.Project.TABLE_NAME+" ("+ Contract.Project._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.Project.PROJECT_NAME+" VARCHAR2(20) NOT NULL ," +
                Contract.Project.PROJECT_DESCRIPTION+" TEXT NOT NULL ," +
                Contract.Project.PROJECT_DATE+" DATE  )";

        final String req_task = "create table "+ Contract.Task.TABLE_NAME+" ("+ Contract.Task._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.Task.TASK_NAME+" VARCHAR(20) NOT NULL," +
                Contract.Task.TASK_DESCRIPTION+" TEXT NOT NULL," +
                Contract.Task.TASK_STATUS+" VARCHAR(20) NOT NULL ," +
                Contract.Task.TASK_DATE+" DATE ," +
                Contract.Task.TASK_PROJECT+" INTEGER NOT NULL REFERENCES "+ Contract.Project.TABLE_NAME+"("+ Contract.Project._ID+"))";

        sqLiteDatabase.execSQL(req_project);
        sqLiteDatabase.execSQL(req_task);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.Project.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.Task.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
