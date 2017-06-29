package com.tyc.tdribbble.ui.shotsdetails.introduction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.tyc.tdribbble.R;
import com.tyc.tdribbble.entity.ShotsEntity;
import com.tyc.tdribbble.ui.search.SearchActivity;
import com.tyc.tdribbble.ui.user.UserActivity;
import com.tyc.tdribbble.utils.DisplayUtils;
import com.tyc.tdribbble.utils.HtmlFormatUtils;
import com.tyc.tdribbble.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：tangyc on 2017/6/23
 * 邮箱：874500641@qq.com
 */
public class ShotsIntroductionFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.fl_tags)
    FlexboxLayout mFlTags;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_attachments)
    TextView mTvAttachments;
    private ShotsEntity shots;

    public static ShotsIntroductionFragment newInstance(ShotsEntity shotsEntity) {
        ShotsIntroductionFragment fragment = new ShotsIntroductionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shots", shotsEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_shots_introduction, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shots = (ShotsEntity) getArguments().getSerializable("shots");
        String avatar = shots.getUser().getAvatarUrl();
        Glide.with(getActivity()).load(avatar).into(mIvAvatar);
        String location = shots.getUser().getLocation();
        if (TextUtils.isEmpty(location)) {
            mTvLocation.setVisibility(View.GONE);
        } else {
            mTvLocation.setText(location);
        }
        String name = shots.getUser().getName();
        mTvName.setText(name);
        String time = shots.getUpdatedAt();
        mTvTime.setText(TimeUtils.getTimeFromISO8601(time) + "创建");
        if (shots.getTags() != null) {
            for (int i = 0; i < shots.getTags().size(); i++) {
                String tag = shots.getTags().get(i);
                mFlTags.addView(createNewFlexItemTextView(tag));
            }
        }
        String desc = shots.getDescription();
        HtmlFormatUtils.Html2StringNoP(mTvDesc, desc);
        int attachmentsCount = shots.getAttachmentsCount();
        if (attachmentsCount > 0) {
            mTvAttachments.setText(attachmentsCount + "个插件");
        } else {
            mTvAttachments.setVisibility(View.GONE);
        }
    }

    /**
     * 动态创建TextView
     *
     * @return
     */
    private TextView createNewFlexItemTextView(final String tag) {
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);
        textView.setText(tag);
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.bg_linear_tag);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                intent.putExtra("search", tag);
                startActivity(intent);
            }
        });
        int padding = DisplayUtils.dip2px(getActivity(), 4);
        int paddingLeftAndRight = DisplayUtils.dip2px(getActivity(), 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DisplayUtils.dip2px(getActivity(), 6);
        int marginTop = DisplayUtils.dip2px(getActivity(), 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.iv_avatar, R.id.tv_attachments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar: {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserActivity.class);
                intent.putExtra("user", shots.getUser());
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create((View) mIvAvatar, getResources().getString(R.string.str_avatar_tran)),
                        Pair.create((View) mTvName, getResources().getString(R.string.str_name_tran))).toBundle());
                break;
            }
            case R.id.tv_attachments:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(shots.getAttachmentsUrl());
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }
}
