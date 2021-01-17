package il.co.expertize.navigationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;

/**
 * Custom list adapter, implementing BaseAdapter
 */
public class CustomListAdapterRegisteredTravels extends BaseAdapter {
    private final Context context;
    private final ArrayList<Travel> items;
    private CompanyTravelListener listener;

    public CustomListAdapterRegisteredTravels(Context context, ArrayList<Travel> items) {
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
        viewHolder.numOfPassengers.setText(currentItem.getNumOfPassengers());
        viewHolder.beginDate.setText(currentItem.getTravelDate().toString());
        viewHolder.endDate.setText(currentItem.getArrivalDate().toString());
        viewHolder.applicationDate.setText(currentItem.getApplicationDate());

//        //viewHolder.call.setTag(R.integer.call_view, convertView);
//        viewHolder.hireSociety.setTag(R.integer.hireSociety_pos, position);
//        viewHolder.hireSociety.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //    View tempview = (View) viewHolder.call.getTag(R.integer.call_view);
//                Integer pos = (Integer) viewHolder.hireSociety.getTag(R.integer.hireSociety_pos);
//                if (listener!=null)
//                    listener.onButtonClicked(pos,view);
//            }
//        });
//
//
//        //  viewHolder.update.setTag(R.integer.update_view, convertView);
//        viewHolder.startTravel.setTag(R.integer.startTravel_pos, position);
//        viewHolder.startTravel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //     View tempview = (View) viewHolder.update.getTag(R.integer.update_view);
//                Integer pos = (Integer) viewHolder.startTravel.getTag(R.integer.startTravel_pos);
//                if (listener!=null)
//                    listener.onButtonClicked(pos,view);
//            }
//        });
//
//        //  viewHolder.update.setTag(R.integer.update_view, convertView);
//        viewHolder.finishTravel.setTag(R.integer.finishTravel_pos, position);
//        viewHolder.finishTravel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //     View tempview = (View) viewHolder.update.getTag(R.integer.update_view);
//                Integer pos = (Integer) viewHolder.finishTravel.getTag(R.integer.finishTravel_pos);
//                if (listener!=null)
//                    listener.onButtonClicked(pos,view);
//            }
//        });

        return convertView;
    }

    //ViewHolder inner class
    private static class ViewHolder {
        TextView clientName;
        TextView numOfPassengers;
        TextView beginDate;
        TextView endDate;
        TextView applicationDate;

        Button  hireSociety;
        Button startTravel;
        Button finishTravel;

        Spinner companiesSpinner;

        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.clientName);
            numOfPassengers = (TextView) view.findViewById(R.id.num_of_passengers);
            beginDate = (TextView) view.findViewById(R.id.begin_date);
            endDate = (TextView) view.findViewById(R.id.end_date);
            applicationDate = (TextView) view.findViewById(R.id.application_date);

            hireSociety=(Button)view.findViewById(R.id.accepted_travel);
            startTravel=(Button)view.findViewById(R.id.travel_start);
            finishTravel=(Button)view.findViewById(R.id.travel_finish);

            companiesSpinner = (Spinner)view.findViewById(R.id.travel_companies_spinner);
        }
    }
}
