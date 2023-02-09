package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dao.daotv.R;
import com.dao.daotv.clear.Cache_Data_Clear;
import com.dao.daotv.function.function_azyy;
import com.dao.daotv.function.function_dzxy;
import com.dao.daotv.function.function_ftxd;
import com.dao.daotv.function.function_htry;
import com.dao.daotv.function.function_qmdj;
import com.dao.daotv.function.function_sdcb;
import com.dao.daotv.function.function_tiandao;
import com.dao.daotv.function.function_xlqk;
import com.dao.daotv.function.gbook;
import com.dao.daotv.public_class;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class Fragment_My extends Fragment {

    private View view;
    private List<String> fun_lists;
    private List<String> fun_imgs;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment__my, container, false);
            fun_lists = new ArrayList<>();
            fun_imgs = new ArrayList<>();
            fun_lists.add("天道☯模式");
            fun_lists.add("抱朴含真");
            fun_lists.add("耳视目听");
            fun_lists.add("奇门遁甲");
            fun_lists.add("撒豆成兵");
            fun_lists.add("法天象地");
            fun_lists.add("安镇阴阳");
            fun_lists.add("壶天日月");
            fun_lists.add("袖里乾坤");
            fun_lists.add("步斗踏罡");
            fun_lists.add("斗转星移");
            fun_lists.add("五雷轰顶");
            fun_lists.add("九天荡魔");
            fun_lists.add("万鬼伏藏");
            fun_lists.add("八九玄功");
            fun_lists.add("三味真火");
            fun_lists.add("三头六臂");
            fun_lists.add("呼风唤雨");
            fun_lists.add("元神出窍");
            fun_lists.add("身死道消");
            fun_imgs.add(String.valueOf(R.drawable.function_dao));
            fun_imgs.add(String.valueOf(R.drawable.function_clear1));
            fun_imgs.add(String.valueOf(R.drawable.function_gbook));
            fun_imgs.add(String.valueOf(R.drawable.function_qmdj));
            fun_imgs.add(String.valueOf(R.drawable.function_sdcb));
            fun_imgs.add(String.valueOf(R.drawable.function_ftxd));
            fun_imgs.add(String.valueOf(R.drawable.function_yinyang));
            fun_imgs.add(String.valueOf(R.drawable.function_hzry));
            fun_imgs.add(String.valueOf(R.drawable.function_xlqk));
            fun_imgs.add(String.valueOf(R.drawable.function_bdtg));
            fun_imgs.add(String.valueOf(R.drawable.function_dzxy));
            fun_imgs.add(String.valueOf(R.drawable.function_wlhd));
            fun_imgs.add(String.valueOf(R.drawable.function_jtdm));
            fun_imgs.add(String.valueOf(R.drawable.function_zk));
            fun_imgs.add(String.valueOf(R.drawable.function_bjxg));
            fun_imgs.add(String.valueOf(R.drawable.function_swzh));
            fun_imgs.add(String.valueOf(R.drawable.function_stlb));
            fun_imgs.add(String.valueOf(R.drawable.function_hfhy));
            fun_imgs.add(String.valueOf(R.drawable.function_yscq));
            fun_imgs.add(String.valueOf(R.drawable.function_fu));
            load();
        }
        return view;
    }

    public void load(){
        list_create((RecyclerView)view.findViewById(R.id.fun_list));
    }

    public void list_create(RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        fun_class fun_class = new fun_class(getContext(),fun_lists,fun_imgs);
        fun_class.setOnItemClickListener(new fun_class.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                function_run(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        recyclerView.setAdapter(fun_class);
    }

    public void function_run(int position){

        if (position==0){
            startActivity(new Intent(getActivity(), function_tiandao.class));
            return;
        }

        if (position==1){
            Cache_Data_Clear.clearAllCache(getContext());
            Toast.makeText(getContext(),"返璞归真",Toast.LENGTH_SHORT).show();
            return;
        }
        if (position==2){
            startActivity(new Intent(getActivity(), gbook.class));
            return;
        }

        if (public_class.dao_gbook.equals("1")){
            if (position==3){
                startActivity(new Intent(getActivity(), function_sdcb.class));
                return;
            }
            if (position==4){
                startActivity(new Intent(getActivity(), function_qmdj.class));
                return;
            }
            if (position==5){
                startActivity(new Intent(getActivity(), function_ftxd.class));
                return;
            }
            if (position==6){
                startActivity(new Intent(getActivity(), function_azyy.class));
                return;
            }
            if (position==7){
                startActivity(new Intent(getActivity(), function_htry.class));
                return;
            }
            if (position==8){
                startActivity(new Intent(getActivity(), function_xlqk.class));
                return;
            }

            if (position==10){
                startActivity(new Intent(getActivity(), function_dzxy.class));
                return;
            }

        }else {
            public_class.shouToast(getContext(),"修为不足");
            return;
        }
        public_class.shouToast(getActivity(),"功能开发中");
    }



}

class fun_class extends RecyclerView.Adapter<fun_class.MyviewHolder>{

    private OnItemClickListener mOnItemClickListener;
    public List<String> fun_lists;
    public List<String> fun_imgs;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public fun_class(Context context, List<String> fun_lists,List<String> fun_imgs){
        this.context = context;
        this.fun_lists = fun_lists;
        this.fun_imgs = fun_imgs;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.my_function,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Glide.with(context).load(Integer.parseInt(fun_imgs.get(position))).into(holder.imageView);
        holder.textView.setText(fun_lists.get(position));
        if (mOnItemClickListener!=null){
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
        return fun_lists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.fun_text);
            imageView = (ImageView)itemView.findViewById(R.id.fun_img);
        }
    }
}