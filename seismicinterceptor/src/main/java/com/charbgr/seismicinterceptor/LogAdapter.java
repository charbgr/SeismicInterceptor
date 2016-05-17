package com.charbgr.seismicinterceptor;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charbgr.seismicinterceptor.listeners.OnSeismicItemClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

class LogAdapter extends RecyclerView.Adapter<LogAdapter.SeismicViewHolder> {

    private List<Pair<Request, Response>> logList;
    private WeakReference<OnSeismicItemClickListener> onSeismicItemClickListenerRef;


    public LogAdapter(List<Pair<Request, Response>> logList) {
        this.logList = logList;
    }

    @Override
    public SeismicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_request, parent, false);

        return new SeismicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SeismicViewHolder holder, int position) {
        final Pair<Request, Response> log = getItemAt(position);
        final LogItemViewModel viewModel = new LogItemViewModel(
                holder.itemView.getContext(),
                log.first,
                log.second
        );

        holder.bind(viewModel);

        holder.itemView.findViewById(R.id.item_seismic_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSeismicItemClickListenerRef != null && onSeismicItemClickListenerRef.get() != null){
                    onSeismicItemClickListenerRef.get().onSeismicItemClick(
                            log.first, log.second, holder.getAdapterPosition()
                    );
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public Pair<Request, Response> getItemAt(int position) {
        return logList.get(position);
    }

    public void setOnSeismicItemClickListener(OnSeismicItemClickListener onSeismicItemClickListener) {
        if (onSeismicItemClickListener == null) {
            throw new IllegalArgumentException("Callback must not be null");
        } else {
            this.onSeismicItemClickListenerRef = new WeakReference<>(onSeismicItemClickListener);
        }
    }

    public class SeismicViewHolder extends RecyclerView.ViewHolder {

        private final TextView httpVerb;
        private final TextView url;
        private final ImageView httpsBadge;
        private final TextView contentType;

        public SeismicViewHolder(View itemView) {
            super(itemView);
            httpVerb = (TextView) itemView.findViewById(R.id.item_seismic_http_verb);
            url = (TextView) itemView.findViewById(R.id.item_seismic_url);
            httpsBadge = (ImageView) itemView.findViewById(R.id.item_seismic_https_badge);
            contentType = (TextView) itemView.findViewById(R.id.item_seismic_content_type);
        }

        public void bind(LogItemViewModel viewModel) {
            httpVerb.setText(viewModel.getHttpVerb());
            httpVerb.setBackgroundColor(viewModel.getHttpStatusBgColor());
            url.setText(viewModel.getUrl());
            httpsBadge.setColorFilter(viewModel.getHttpsBadgeTintColor());
            contentType.setText(viewModel.getContentType());
        }
    }
}
