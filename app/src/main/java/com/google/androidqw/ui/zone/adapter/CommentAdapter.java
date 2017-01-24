package com.google.androidqw.ui.zone.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.bean.CommentItem;
import com.google.androidqw.ui.zone.spannable.CircleMovementMethod;
import com.google.androidqw.ui.zone.spannable.SpannableClickable;
import com.google.androidqw.ui.zone.widegt.CommentLinearLayout;

import java.util.List;

import utils.ToastUitl;

/**
 * 评论
 * Created by lyz on 2016/12/27.
 * <p/>
 * <p/>
 * 1. 先给我一个LiearLayout的容器
 * 2. 随后给我一个mDatas 实体数据 ,我根据这集合的顺序 生成view
 * 3. 放到容器中
 * TODO 4. 用recycleView来实现是否会更好呢
 */
public class CommentAdapter {
    private CommentLinearLayout commentLayout;
    private List<CommentItem> mDatas;
    private Context mContext;

    public CommentAdapter(Context context) {
        mContext = context;
    }

    public void bindListView(CommentLinearLayout commentLinearLayout) {
        if (null == commentLinearLayout) {
            thorwCommtLayoutIsNull();

        }
        this.commentLayout = commentLinearLayout;
    }

    private void thorwCommtLayoutIsNull() {
        throw new IllegalArgumentException("commentView ......... is  null");
    }

    public void setDatas(List<CommentItem> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mDatas = null;
        notifyDataSetChanged();
    }

    /**
     * @return
     */
    public SpannableStringBuilder setTextViewGreenSpannable(Context context, String strContent) {
        SpannableStringBuilder style = new SpannableStringBuilder(strContent);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_fd9405));
        style.setSpan(colorSpan,
                0, strContent.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 返回当前集合里对应的view
     *
     * @param position
     * @return
     */
    public View getView(final int position) {
        TextView textView = (TextView) View.inflate(mContext, R.layout.item_comment_text, null);
        // STOPSHIP: 2017/1/5  谁评论谁: 首先有一个标示,是否带2个人的名字(就是谁评论谁); 拿到前面的名字和后面的 加上评论的内容
        final CommentItem commentItem = mDatas.get(position);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(R.color.circle_name_selector_color,
                R.color.circle_name_selector_color);

        String toReplyName = "";
        builder.append(setClickableSpan(commentItem.getUserNickname(), commentItem.getUserId(), position));
        //有被评论人的数据
        String toReplyUserid = commentItem.getAppointUserid();
        if (toReplyUserid != null) {
            builder.append(" 回复 ");
            toReplyName = commentItem.getAppointUserNickname();
            builder.append(setClickableSpan(toReplyName, toReplyUserid, position));
        }
        builder.append(" : " + commentItem.getContent());
        textView.setText(builder);
        textView.setMovementMethod(circleMovementMethod);
        /**
         *  1.  如果是点击自己就显示删除和复制 : 如果点击对方就显示输入框,并默认文本为回复谁谁谁
         *
         *  2 . 长按如果是对方就是显示复制,如果是点击自己就显示删除和复制
         */
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUitl.show("onClick");
                if (null != commentLayout.getClickListener() && circleMovementMethod.isPassToTv()) {
                    commentLayout.getClickListener().onItemClick(position);
                }
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUitl.show("onLongClick");
                if (null != commentLayout.getLongClickListener() && circleMovementMethod.isPassToTv()) {
                    commentLayout.getLongClickListener().onLongItemClick(position);
                }
                return true;
            }
        });
        return textView;
    }

    public SpannableString setClickableSpan(String text, final String userUuid, final int position) {
        SpannableString subjectSpanText = new SpannableString(text);
        subjectSpanText.setSpan(new SpannableClickable() {
            @Override
            public void onClick(View widget) {
                ToastUitl.showToastWithImg("登录成功-->" + userUuid, -1);
            }
        }, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }


    //    动态回复 {
//        appointUserNickname (string, optional): @用户昵称 ,
//        appointUserid (integer, optional): @用户ID ,
//        content (string, optional): 回复内容 ,
//                createTime (string, optional): 回复时间 ,
//                id (integer, optional): ID ,
//                pictures (string, optional): 回复图片 ,
//                publishId (integer, optional): 动态ID ,
//                userId (integer, optional): 回复USERID ,
//                userNickname (string, optional): 回复昵称
//    }


    public void notifyDataSetChanged() {
        if (commentLayout == null) {
            thorwCommtLayoutIsNull();
        }
        // TODO: 2016/12/27  这里 可以获取 commentLayout 里的孩子,
        // 如果和下面mDatas里的数据一致就这里就直接返回,不要在删除全部再add里,不知道是否可行
        commentLayout.removeAllViews();
        if (null == mDatas || mDatas.size() == 0) {
            return;
        }

        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;//为了可读性更好
            View view = getView(index);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            commentLayout.addView(view, index, params);
        }

    }
}
