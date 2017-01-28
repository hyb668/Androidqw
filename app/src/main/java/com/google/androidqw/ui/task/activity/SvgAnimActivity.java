package com.google.androidqw.ui.task.activity;

import android.graphics.Path;

import com.google.androidqw.R;
import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.utils.SvgPathParser;

import java.text.ParseException;

import base.BaseActivity;

public class SvgAnimActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_svg_anim;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        LoadingPathAnimView loadingPathAnimView = (LoadingPathAnimView) findViewById(R.id.loadingPath);
        loadingPathAnimView.startAnim();
        PathAnimView pathTomorrowView = (PathAnimView) findViewById(R.id.pathView_tomorrow);
        String qianbihua = getString(R.string._tomorrow_big);
        SvgPathParser svgPathParser = new SvgPathParser();
        try {
            Path path = svgPathParser.parsePath(qianbihua);
            pathTomorrowView.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pathTomorrowView.getPathAnimHelper().setAnimTime(1000);
        pathTomorrowView.startAnim();

        PathAnimView pathPahtView = (PathAnimView) findViewById(R.id.pathView_pay);
        String _milk = getString(R.string._milk);
        SvgPathParser pathParser = new SvgPathParser();
        try {
            Path path = pathParser.parsePath(_milk);
            pathPahtView.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pathPahtView.getPathAnimHelper().setAnimTime(1000);
        pathPahtView.startAnim();
    }

    public void startAnim(PathAnimView pathAnimView, long duration,String pathStr) {
        SvgPathParser pathParser = new SvgPathParser();
        try {
            Path path = pathParser.parsePath(pathStr);
            pathAnimView.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pathAnimView.getPathAnimHelper().setAnimTime(duration);
        pathAnimView.startAnim();
    }
}
