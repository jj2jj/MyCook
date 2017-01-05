package zjj.com.mycookassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zjj.com.mycookassistant.R;
import zjj.com.mycookassistant.activity.DetailTypeActivity;

/**
 * sihuan.com.mycookassistant.fragment
 * Created by sihuan on 2016/10/25.
 * 分类
 */

public class ClassificationFragment extends Fragment {
    private List<Map<String, Object>> dishtype_list;
    String[] dishes_type;

    // 图片封装为一个数组
    private int[] icon = {R.drawable.xiafancai, R.drawable.biandang,
            R.drawable.jiacahngcai, R.drawable.sushi, R.drawable.tianpin,
            R.drawable.shala, R.drawable.lanrenshipu, R.drawable.tangpin,
            R.drawable.mianshi};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_classification_view, container, false);
        GridView gview = (GridView) view.findViewById(R.id.gridview);
        //新建List
        dishtype_list = new ArrayList<>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image_class, R.id.text_class};
        SimpleAdapter sim_adapter = new SimpleAdapter(getContext(), dishtype_list, R.layout.item_classification, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);

        intentEvents(gview);
        return view;
    }

    private void intentEvents(GridView gview) {
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailTypeActivity.class);
                switch (icon[i]) {
                    case R.drawable.xiafancai:
//                       //跳转界面
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.biandang:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.jiacahngcai:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.sushi:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.tianpin:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.shala:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.lanrenshipu:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.tangpin:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    case R.drawable.mianshi:
                        intent.putExtra("dishes_type", dishes_type[i]);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public List<Map<String, Object>> getData() {
        dishes_type = this.getResources().getStringArray(R.array.dishesType);
        //icon和dishestype的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", dishes_type[i]);
            dishtype_list.add(map);
        }
        return dishtype_list;
    }
}
