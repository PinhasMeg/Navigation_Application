package il.co.expertize.navigationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;

/**
 * Custom list adapter, implementing BaseAdapter
 */
public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private CompanyTravelListener listener;

    public CustomListAdapter(Context context, ArrayList<Travel> items) {
        this.context = context;
        this.items = items;
    }

    public interface CompanyTravelListener {
        void onButtonClicked(int position, View view);
    }

    public void setListener(CompanyTravelListener listener){
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total item in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns the item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rowitem_registeredtravels, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Travel currentItem = (Travel) getItem(position);
        viewHolder.clientName.setText(currentItem.getClientName());
        viewHolder.clientPhone.setText(currentItem.getClientPhone());
        viewHolder.clientEmail.setText(currentItem.getClientEmail());



        //viewHolder.call.setTag(R.integer.call_view, convertView);
        viewHolder.call.setTag(R.integer.call_pos, position);
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    View tempview = (View) viewHolder.call.getTag(R.integer.call_view);
                Integer pos = (Integer) viewHolder.call.getTag(R.integer.call_pos);
                if (listener!=null)
                    listener.onButtonClicked(pos,view);
            }
        });


        //  viewHolder.update.setTag(R.integer.update_view, convertView);
        viewHolder.update.setTag(R.integer.update_pos, position);
        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     View tempview = (View) viewHolder.update.getTag(R.integer.update_view);
                Integer pos = (Integer) viewHolder.update.getTag(R.integer.update_pos);
                if (listener!=null)
                    listener.onButtonClicked(pos,view);
            }
        });

        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView clientName;
        TextView clientPhone;
        TextView clientEmail;
        Button  call;
        Button update;

        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.clientName);
            clientPhone = (TextView) view.findViewById(R.id.clientPhone);
            clientEmail = (TextView) view.findViewById(R.id.clientEmail);
            call=(Button)view.findViewById(R.id.bt_call);
            update=(Button)view.findViewById(R.id.bt_update);
        }
    }
}
