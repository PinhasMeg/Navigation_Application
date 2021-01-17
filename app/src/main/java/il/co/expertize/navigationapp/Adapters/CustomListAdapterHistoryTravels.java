package il.co.expertize.navigationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;

public class CustomListAdapterHistoryTravels extends BaseAdapter {
    private final Context context;
    private final ArrayList<Travel> items;
    private CompanyTravelListener listener;

    public CustomListAdapterHistoryTravels(Context context, ArrayList<Travel> items) {
        this.context = context;
        this.items = items;
    }

    public interface CompanyTravelListener {
        void onButtonClicked(int position, View view);
    }

    public void setListener(CustomListAdapterHistoryTravels.CompanyTravelListener listener) {
        this.listener = listener;
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
        CustomListAdapterHistoryTravels.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rowitem_historytravels, parent, false);
            viewHolder = new CustomListAdapterHistoryTravels.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomListAdapterHistoryTravels.ViewHolder) convertView.getTag();
        }

        Travel currentItem = (Travel) getItem(position);
        viewHolder.clientName.setText(currentItem.getClientName());
        Period periodBetween = Period.between(LocalDate.parse(currentItem.getTravelDate()), LocalDate.parse(currentItem.getArrivalDate()));
        String period = periodBetween.getYears()+ " years, " + periodBetween.getMonths()+ " months, " + periodBetween.getDays()+ " days";
        viewHolder.totalDaysOfTravel.setText(period);


       //viewHolder.sendEmail.setTag(R.integer.call_view, convertView);
        viewHolder.paidTravels.setTag(R.integer.paidTravels_pos, position);
        viewHolder.paidTravels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    View tempview = (View) viewHolder.call.getTag(R.integer.call_view);
                Integer pos = (Integer) viewHolder.paidTravels.getTag(R.integer.paidTravels_pos);
                if (listener!=null)
                    listener.onButtonClicked(pos,view);
            }
        });


        //  viewHolder.update.setTag(R.integer.update_view, convertView);
        viewHolder.sendEmail.setTag(R.integer.sendEmail_pos, position);
        viewHolder.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     View tempview = (View) viewHolder.update.getTag(R.integer.update_view);
                Integer pos = (Integer) viewHolder.sendEmail.getTag(R.integer.sendEmail_pos);
                if (listener!=null)
                    listener.onButtonClicked(pos,view);
            }
        });


        return convertView;
    }

    //ViewHolder inner class
    private static class ViewHolder {
        TextView clientName;
        TextView totalDaysOfTravel;
        Button sendEmail;
        Button paidTravels;

        public ViewHolder(View view) {
            clientName = (TextView) view.findViewById(R.id.clientName);
            totalDaysOfTravel = (TextView) view.findViewById(R.id.travel_days);

            sendEmail = (Button) view.findViewById(R.id.send_mail_society);
            paidTravels = (Button) view.findViewById(R.id.validate_after_payment);
        }
    }
}
