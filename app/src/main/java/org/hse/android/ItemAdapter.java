package org.hse.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static org.hse.android.ScheduleActivity.*;

public final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final static int TYPE_ITEM = 0;
        private final static int TYPE_HEADER = 1;

        private List<ScheduleItem> dataList = new ArrayList<>();
        private OnItemClick onItemClick;

        public ItemAdapter(OnItemClick onItemClick) { this.onItemClick = onItemClick; }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            if (viewType == TYPE_ITEM) {
                View contactView = inflater.inflate(R.layout.item_schedule, parent, false);
                return new ViewHolder(contactView, context, onItemClick);
            }
            else if (viewType == TYPE_HEADER) {
                View contactView = inflater.inflate(R.layout.item_schedule_header, parent, false);
                return new ViewHolderHeader(contactView, context, onItemClick);
            }
            throw new IllegalArgumentException("Invalid view type");
        }

        public int getItemViewType(int position) {
            ScheduleItem data = dataList.get(position);
            if (data instanceof ScheduleItemHeader) { return TYPE_HEADER; }
            return TYPE_ITEM;
        }

        public void setDataList(List<ScheduleItem> list) {
            this.dataList = new ArrayList<>();
            if (dataList != null) { this.dataList.addAll(list); }
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            ScheduleItem data = dataList.get(position);
            if (viewHolder instanceof ViewHolder) {
                ((ViewHolder) viewHolder).bind(data);
            }
            else if (viewHolder instanceof ViewHolderHeader) {
                ((ViewHolderHeader) viewHolder).bind((ScheduleItemHeader) data);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
