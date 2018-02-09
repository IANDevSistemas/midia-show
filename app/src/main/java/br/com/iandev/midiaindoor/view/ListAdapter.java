package br.com.iandev.midiaindoor.view;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Lucas on 09/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 09/04/2017  Lucas
 */

public abstract class ListAdapter<T> extends BaseAdapter {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
