package com.example.joginderpal.railway_enquiry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joginderpal on 20-01-2017.
 */
public class RecyclerAdapterone extends RecyclerView.Adapter<RecyclerAdapterone.ViewHolder> {

    private String[] titles={
            "Classes Availability",
            "Available days",
            "Route"
    };
    Context ctx;
    List<String> avail;
    List<String> classcod;

    //  public RecyclerAdapter(Context ctx) {
    //     this.ctx=ctx;
    // }

    public RecyclerAdapterone(List<String> avail, List<String> classcod) {

        this.avail=avail;
        this.classcod=classcod;

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public TextView available;
        public TextView classcode;

        public ViewHolder(View itemView) {
            super(itemView);
            available= (TextView) itemView.findViewById(R.id.tx2);
            classcode= (TextView) itemView.findViewById(R.id.tx3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position==0){
                        //    Intent intent=new Intent(ctx,classes.class);
                        // ctx.startActivity(intent);
                    }
                /*    Intent intent=new Intent(ctx,MapsActivity.class);
                    intent.putExtra("latadd",l3.get(position));
                    intent.putExtra("lonadd",l4.get(position));
                    intent.putExtra("name",l2.get(position));
                    intent.putExtra("icon",l5.get(position));
                    intent.putExtra("passlink","restaurant");
                    ctx.startActivity(intent);
*/
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_one,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.available.setText(avail.get(position));
        if(classcod.get(position).equals("N")){
            holder.classcode.setText("NO");
        }
        else if (classcod.get(position).equals("Y")){
            holder.classcode.setText("YES");
        }
    }

    @Override
    public int getItemCount() {
        return avail.size();
    }
}


