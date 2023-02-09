package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dao.daotv.R;
import com.dao.daotv.public_class;
import com.dao.daotv.sql.App;
import com.dao.daotv.sql.DaoSession;
import com.dao.daotv.sql.Video;
import com.dao.daotv.sql.VideoDao;
import com.dao.daotv.video_list_player;
import com.dao.daotv.video_player;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;

public class Fragment_list extends Fragment {



    private View view;
    private App app;
    private DaoSession daoSession;
    private VideoDao videoDao;
    private List<Video> videoList;


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
        }
        public_class.select_int = new LinkedList<>();
        load();

        return view;
    }


    public void load(){
        public_class.select_int.clear();
        app = App.getApp();
        daoSession = app.getDaoSession();
        videoDao = daoSession.getVideoDao();
        videoList = videoDao.queryBuilder().list();
        Collections.reverse(videoList);
        list((RecyclerView)view.findViewById(R.id.select_list));
    }

    public void list(RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        select_myadapter select_myadapter = new select_myadapter(getContext(),videoList);
        select_myadapter.setOnItemClickListener(new select_myadapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                public_class.Video_id = videoList.get(position).getVideo_id();
                startActivity(new Intent(getActivity(),video_list_player.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {}
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0){
                    public_class.shouToast(getContext(),"已加载全部记录");
                }

            }
        });
        recyclerView.setAdapter(select_myadapter);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                select_myadapter.notifyDataSetChanged();
            }
        });


        TextView textView = (TextView)view.findViewById(R.id.text_select);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        Button button = (Button)view.findViewById(R.id.all_select);
        Button button1 = (Button)view.findViewById(R.id.delete_select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                public_class.select_int.clear();
                for (int i=0;i<videoList.size();i++){
                    public_class.select_int.add(i);
                }
                Collections.sort(public_class.select_int);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        select_myadapter.notifyDataSetChanged();
                    }
                });
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(public_class.select_int);
                for (int i = 0;i<public_class.select_int.size();i++){
                    videoDao.deleteByKey((Long) videoList.get(public_class.select_int.get(i)).getId());
                }

                public_class.select_int.clear();
                videoList.clear();


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        select_myadapter.notifyDataSetChanged();
                    }
                });

                load();
            }
        });
    }


}

class select_myadapter extends RecyclerView.Adapter<select_myadapter.MyviewHolder>{

    private OnItemClickListener mOnItemClickListener;
    public List<Video> video_lists;
    private Context context;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public select_myadapter(Context context, List<Video> video_lists){
        this.video_lists = video_lists;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.checkBox.setVisibility(View.VISIBLE);
        if (public_class.select_int.contains(position)){
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    public_class.select_int.add(position);
                    Collections.sort(public_class.select_int);
                }else {
                    public_class.select_int.remove((Integer) position);
                    Collections.sort(public_class.select_int);
                }
            }
        });
        Glide.with(context).load(video_lists.get(position).getVideo_img()).into(holder.image);
        holder.number.setText(String.valueOf(video_lists.get(position).getVideo_mark()));
        holder.name.setText(video_lists.get(position).getVideo_name());
        holder.title.setText(video_lists.get(position).getVideo_play());
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return video_lists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView name,title,number;
        ImageView image;
        CheckBox checkBox;
        public MyviewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.video_title);
            title = view.findViewById(R.id.video_small_title);
            number = view.findViewById(R.id.video_number);
            image = view.findViewById(R.id.video_image);
            checkBox = view.findViewById(R.id.select_delete);
        }
    }
}