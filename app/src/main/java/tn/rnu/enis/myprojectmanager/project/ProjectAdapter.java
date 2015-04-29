package tn.rnu.enis.myprojectmanager.project;

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
public class ProjectAdapter extends CursorAdapter {
    public ProjectAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.projectitemmain, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(ProjectsFragment.COL_PROJECT_NAME);
        String description = cursor.getString(ProjectsFragment.COL_PROJECT_DESCRIPTION);
        String date = cursor.getString(ProjectsFragment.COL_PROJECT_DATE);

        viewHolder.nameView.setText(name);
        viewHolder.descriptionView.setText(description);
        viewHolder.dateView.setText(date);

    }

    public static class ViewHolder {
        public final TextView nameView;
        public final TextView descriptionView;
        public final TextView dateView;

        public ViewHolder(View view) {
            dateView = (TextView) view.findViewById(R.id.date);
            descriptionView = (TextView) view.findViewById(R.id.description);
            nameView = (TextView) view.findViewById(R.id.name);
        }
    }
}
