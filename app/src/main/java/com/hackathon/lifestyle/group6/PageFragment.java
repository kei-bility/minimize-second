package com.hackathon.lifestyle.group6;

/**
 * Created by keisuke-yamaya on 2017/02/18.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PageFragment extends Fragment {

    private static final String ARG_PARAM = "page";
    private String mParam;
    private OnFragmentInteractionListener mListener;
    private MyListener myListener;
    private static Boolean[] is_colored = {false, false, false, false, false};

    // コンストラクタ
    public PageFragment() {
    }

    public interface MyListener {
        void onClickButton();
        void changeColor(View v);
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int page = getArguments().getInt(ARG_PARAM, 0);
        // page によってinflateするlayoutを変える
        View view;
        //view = inflater.inflate(R.layout.first_page, container, false);
        switch (page) {
            case 1:
                view = inflater.inflate(R.layout.first_page, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.second_page, container, false);
                setSelectedColor(view);
                break;
            case 3:
                view = inflater.inflate(R.layout.third_page, container, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.fourth_page, container, false);
                break;
            case 5:
                view = inflater.inflate(R.layout.fifth_page, container, false);
                //Button b = (Button) view.findViewById(R.id.ok_button);
                view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myListener != null) {
                            myListener.onClickButton();
                        }
                    }
                });
                break;
            default:
                view = inflater.inflate(R.layout.fragment_page, container, false);
        }
        //TextView tv = (TextView)view.findViewById(R.id.textView);
        //tv.setText("Page" + page);
        Log.d("test", Integer.toString(page));
        return view;
    }

    public void setSelectedColor(View v) {
        v.findViewById(R.id.where1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v.findViewById(R.id.where1);
                if (is_colored[0] == false) {
                    b.setBackgroundColor(Color.rgb(76, 175, 80));
                    b.setTextColor(Color.rgb(255, 255, 255));
                    is_colored[0] = true;
                } else {
                    b.setBackgroundColor(Color.rgb(255, 255, 255));
                    b.setTextColor(Color.rgb(0, 0, 0));
                    is_colored[0] = false;
                }
            }
        });
        v.findViewById(R.id.where2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v.findViewById(R.id.where2);
                if (is_colored[1] == false) {
                    b.setBackgroundColor(Color.rgb(76, 175, 80));
                    b.setTextColor(Color.rgb(255, 255, 255));
                    is_colored[1] = true;
                } else {
                    b.setBackgroundColor(Color.rgb(255, 255, 255));
                    b.setTextColor(Color.rgb(0, 0, 0));
                    is_colored[1] = false;
                }
            }
        });
        v.findViewById(R.id.where3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v.findViewById(R.id.where3);
                if (is_colored[2] == false) {
                    b.setBackgroundColor(Color.rgb(76, 175, 80));
                    b.setTextColor(Color.rgb(255, 255, 255));
                    is_colored[2] = true;
                } else {
                    b.setBackgroundColor(Color.rgb(255, 255, 255));
                    b.setTextColor(Color.rgb(0, 0, 0));
                    is_colored[2] = false;
                }
            }
        });
        v.findViewById(R.id.where4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v.findViewById(R.id.where4);
                if (is_colored[3] == false) {
                    b.setBackgroundColor(Color.rgb(76, 175, 80));
                    b.setTextColor(Color.rgb(255, 255, 255));
                    is_colored[3] = true;
                } else {
                    b.setBackgroundColor(Color.rgb(255, 255, 255));
                    b.setTextColor(Color.rgb(0, 0, 0));
                    is_colored[3] = false;
                }
            }
        });
        v.findViewById(R.id.where5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v.findViewById(R.id.where5);
                if (is_colored[4] == false) {
                    b.setBackgroundColor(Color.rgb(76, 175, 80));
                    b.setTextColor(Color.rgb(255, 255, 255));
                    is_colored[4] = true;
                } else {
                    b.setBackgroundColor(Color.rgb(255, 255, 255));
                    b.setTextColor(Color.rgb(0, 0, 0));
                    is_colored[4] = false;
                }
            }
        });
    }

    /*
        スマートウォッチへ通知を送る
     */
    public void sendBasicNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("新着メッセージがあります")
                .setContentText("スケジュールが追加されました！")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(R.mipmap.ic_launcher, "返信", pIntent)
                .addAction(R.mipmap.ic_launcher, "転送", pIntent)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notification);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClickButton() {
        if (myListener != null) {
            //Toast.makeText(getContext(), "OKボタンがタップされました！", Toast.LENGTH_SHORT).show();
            Log.d("b", "myListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    /*
        Button button = (Button)getActivity().findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "hoge!", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        // contextクラスがMyListenerを実装しているかをチェックする
        if (context instanceof MyListener) {
            // リスナーをここでセットするようにします
            myListener = (MyListener) context;
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListener != null) {
                    myListener.onClickButton();
                }
            }
        });
        */

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        // 画面からFragmentが離れたあとに処理が呼ばれることを避けるためにNullで初期化しておく
        myListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}