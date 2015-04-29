package tn.rnu.enis.myprojectmanager.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed on 27/04/2015.
 */
public class Contract {

    public static final String CONTENT_AUTHORITY = "tn.rnu.enis.myprojectmanager.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PROJECTS = "PROJECT";
    public static final String PATH_TASKS = "TASK";

    public static class Project implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;
        public static final String PROJECTS_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;

        public static final String TABLE_NAME = PATH_PROJECTS;
        //PROJECT FIELDS

        public static final String PROJECT_NAME = "name";
        public static final String PROJECT_DESCRIPTION = "description";
        public static final String PROJECT_DATE = "date";

        public static Uri buildProjectUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class Task implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;
        public static final String TASKS_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;

        public static final String TABLE_NAME = PATH_TASKS;
        //TASK FIELDS

        public static final String TASK_NAME = "name";
        public static final String TASK_DESCRIPTION = "description";
        public static final String TASK_DATE = "date";
        public static final String TASK_STATUS = "status";
        public static final String TASK_PROJECT = "project";

        public static Uri buildTASKUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static String getIDFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static class Status {
        public static final String WAITING = "waiting";
        public static final String DOING = "doing";
        public static final String BLOCKED = "blocked";
        public static final String DONE = "done";
    }

    public static String REF = "REF";
    public static String NAME = "NAME";
}

