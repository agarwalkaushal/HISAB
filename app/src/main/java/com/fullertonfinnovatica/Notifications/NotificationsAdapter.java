package com.fullertonfinnovatica.Notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.RecyclerViewHolder>{

    List<NotificationsModel> list;
    Context context;
    View vview;
    NotificationsModel mRecentlyDeletedItem;
    int mRecentlyDeletedItemPosition;


    public NotificationsAdapter(List<NotificationsModel> arrayList2, Context context, View view)
    {
        this.list=arrayList2;
        this.context = context;
        vview = view;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_notifications, viewGroup, false);
        NotificationsAdapter.RecyclerViewHolder recyclerViewHolder = new NotificationsAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        NotificationsModel modelList = list.get(i);

        recyclerViewHolder.title.setText(modelList.getTitle());
        recyclerViewHolder.body.setText(modelList.getBody());

        if(modelList.getImg().equals("expiry")){
            recyclerViewHolder.img.setImageResource(R.drawable.expired);
        }
        else if(modelList.getImg().equals("threshold")){
            recyclerViewHolder.img.setImageResource(R.drawable.threshold);
        }
        else{
            recyclerViewHolder.img.setImageResource(R.drawable.money);
        }


    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = list.get(position);
        mRecentlyDeletedItemPosition = position;
        list.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = vview.findViewById(R.id.holder);
        Snackbar snackbar = Snackbar.make(view, "Sure?",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        list.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Context getContext() {
        return context;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        ImageView img;

        public RecyclerViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.notificationsTitle);
            body = view.findViewById(R.id.notificationBody);
            img = view.findViewById(R.id.notificationImg);

        }
    }
}
