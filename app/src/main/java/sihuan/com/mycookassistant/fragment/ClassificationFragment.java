package sihuan.com.mycookassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sihuan.com.mycookassistant.R;

/**
 * sihuan.com.mycookassistant.fragment
 * Created by sihuan on 2016/10/25.
 */

public class ClassificationFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_classification_view, container, false);
//
//        ImageView xiafancai = (ImageView) view.findViewById(R.id.xiafancai);
//        ImageView biandang = (ImageView) view.findViewById(R.id.biandang);
//        ImageView sushi = (ImageView) view.findViewById(R.id.sushi);
//        ImageView jiacangcai = (ImageView) view.findViewById(R.id.jiachangcai);
//        ImageView tianpin = (ImageView) view.findViewById(R.id.tianpin);
//        ImageView shala = (ImageView) view.findViewById(R.id.shala);
//        ImageView lanren = (ImageView) view.findViewById(R.id.lanrenshipu);
//        ImageView miansshi = (ImageView) view.findViewById(R.id.mianshi);
//        ImageView tangpin = (ImageView) view.findViewById(R.id.tangpin);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiafancai:

                break;
            case R.id.biandang:
                break;
            case R.id.sushi:
                break;
            case R.id.jiachangcai:
                break;
            case R.id.tianpin:
                break;
            case R.id.shala:
                break;
            case R.id.lanrenshipu:
                break;
            case R.id.mianshi:
                break;
            case R.id.tangpin:
                break;
            default:
                break;
        }
    }
}
