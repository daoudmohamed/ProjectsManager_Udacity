package tn.rnu.enis.myprojectmanager.task;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tn.rnu.enis.myprojectmanager.R;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class TaskAdapter extends CursorAdapter {
    public TaskAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.taskitemmain, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(TasksFragment.COL_TASK_NAME);
        String date = cursor.getString(TasksFragment.COL_TASK_DATE);
        String Status = cursor.getString(TasksFragment.COL_TASK_STATUS);

        viewHolder.nameView.setText(name);
        viewHolder.dateView.setText(date);
        viewHolder.statusView.setText(Status);

    }

    public static class ViewHolder {
        public final TextView nameView;
        public final TextView dateView;
        public final TextView statusView;

        public ViewHolder(View view) {
            dateView = (TextView) view.findViewById(R.id.date);
            nameView = (TextView) view.findViewById(R.id.name);
            statusView = (TextView) view.findViewById(R.id.status);
        }
    }
}
