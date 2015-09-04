package com.xuxu.broadcast;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @ViewById(R.id.textView1)//利用框架初始化TextView，注意不要在前面加private,但是设置为public可以
            TextView tv1;
    @ViewById(R.id.textView2)
    TextView tv2;
    @ViewById
    TextView textView3;//根据xmlID来寻找
//    @ViewById
//    Button button1;

    ProgressDialog pd;

    @AfterViews//表示此方法在view创建之后调用
    public void setView() {
        tv2.setText("view2初始化");
        textView3.setText("view3初始化");
    }

//    @Click(R.id.button1)


//    public void setButton1(View view) {
////        Toast.makeText(this,"toast",Toast.LENGTH_SHORT).show();
//        Snackbar.make(view, "snackbar", Snackbar.LENGTH_LONG).setAction("action", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "snackBar", Toast.LENGTH_SHORT).show();
//            }
//        }).show();
////        Snackbar.make(view, "Snackbar comes out", Snackbar.LENGTH_LONG)
////                .setAction("Action", new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Toast.makeText(
////                                MainActivity.this,
////                                "Toast comes out",
////                                Toast.LENGTH_SHORT).show();
////                    }
////                }).show();
//    }

    @Click(R.id.textView3)
    public void textView3Click() {
        pd = ProgressDialog.show(this, "textView3tile", "tx3Message");
        mHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS, 1000);
//        try {
//            Thread.sleep(1000);
//            pd.dismiss();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static final int MESSAGE_DISMISS = 0X110;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_DISMISS:
                    pd.dismiss();

                    break;
            }
        }
    };

    @ViewById(R.id.button1)
    Button timeBtn;
    Calendar cal=Calendar.getInstance();
    @Click(R.id.button1)
    public void button1CLick(){
        Log.d("", "click the time button to set time");
        TimePickerDialog dialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.e("设置的时间", String.valueOf(hourOfDay));
                Log.e("设置的时间", String.valueOf(minute));
                aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
                Intent intent = new Intent();
                intent.setAction("com.test.BC_ACTION");
                intent.putExtra("msg", "该去开会啦！");
                final PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, 0);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = 18;
                int mi = 40;
                c.set(Calendar.HOUR_OF_DAY, hour);
                Log.e("当前时间", String.valueOf(c.HOUR_OF_DAY));
                c.set(Calendar.MINUTE, mi);
                aManager.set(AlarmManager.RTC_WAKEUP
                        , c.getTimeInMillis(), pi);
                Log.e("闹钟设置成功", String.valueOf(c.HOUR) + String.valueOf(c.MINUTE));
            }
        },cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false);
       dialog.show();
    }
    public static void quickSort(int num[],int start,int end){
//        int end = num.length;
        int base = num[start];
        int temp=0;
        int i=start,j=end;
        while (i<=j){
            while((num[i]<base)&&(i<end))i++;
            while ((num[j]>base)&&(j>start))j--;
            temp = num[i];
            num[i] = num[j];
            num[j]=temp;
            i++;
            j--;
        }
        if (j >start) {
            quickSort(num,start,j);
        }
        if(i<end){
            quickSort(num,i,end);
        }
    }
//    /**
//     * 递归，在反转当前节点之前先反转后续节点
//     *
//     * @param head
//     * @return
//     */
//    public static Node reverse(Node head)
//    {
//        if (null == head || null == head.getNextNode())
//        {
//            return head;
//        }
//        //
//        Node reversedHead = reverse(head.getNextNode());
//        //
//        head.getNextNode().setNextNode(head);
//        //
//        head.setNextNode(null);
//        return reversedHead;
//    }
//
//    /**
//     * 遍历，将当前节点的下一个节点缓存后更改当前节点指针
//     *
//     * @param head
//     * @param mode
//     *            仅用作区分于同名方法
//     * @return
//     */
//    public static Node reverse(Node head, int mode)
//    {
//        if (null == head)
//        {
//            return head;
//        }
//        Node pre = head;
//        Node cur = head.getNextNode();
//        Node next;
//        while (null != cur)
//        {
//            next = cur.getNextNode();
//            cur.setNextNode(pre);
//            pre = cur;
//            cur = next;
//        }
//
//        // 将原链表的头节点的下一个节点置为null，再将反转后的头节点赋给head
//        head.setNextNode(null);
//        head = pre;
//
//        return head;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        double a = 0.0011;
        double b = 0.0011;
        Integer c=12;
        String s = "abc";
        Character r = 'a';
        Character t = new Character('a');
        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        Log.e("双精度比较结果", data1.compareTo(data2)+"");
        Log.e("双精度比较结果", (Math.abs(a-b)<0.000001)+"");
        Log.e("双精度比较结果", Math.abs(a-b)+"");
        Log.e("双精度比较结果", (r==t)+"");
//        setContentView(R.layout.activity_main);
        //快速排序算法
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                createBinTree();
                // nodeList中第0个索引处的值即为根节点
                Node root = nodeList.get(0);

                System.out.println("先序遍历：");
                preOrderTraverse(root);
                System.out.println();

                System.out.println("中序遍历：");
                inOrderTraverse(root);
                System.out.println();

                System.out.println("后序遍历：");
                postOrderTraverse(root);
                System.out.println();
                Log.e("开启了新线程","");
                int num[] = new int[]{-1, -2, 1, 2, 5, 4, 3};
                quickSort(num, 0, num.length-1);

                return String.valueOf(num[0])+String.valueOf(num[2]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("排序之后", s);
            }
        }.execute();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
//        timeBtn = (Button) findViewById(R.id.button1);
//        timeBtn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Log.d("", "click the time button to set time");
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                        Log.e("设置的时间", String.valueOf(hourOfDay));
//                        Log.e("设置的时间", String.valueOf(minute));
//                    }
//                };
//            }
//        });

    }

    private int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private static List<Node> nodeList = null;

    /**
     * 内部类：节点
     *
     * @author ocaicai@yeah.net @date: 2011-5-17
     *
     */
    private static class Node {
        Node leftChild;
        Node rightChild;
        int data;

        Node(int newData) {
            leftChild = null;
            rightChild = null;
            data = newData;
        }
    }

    public void createBinTree() {
        nodeList = new LinkedList<Node>();
        // 将一个数组的值依次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
            nodeList.add(new Node(array[nodeIndex]));
        }
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).leftChild = nodeList
                    .get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).rightChild = nodeList
                    .get(parentIndex * 2 + 2);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = array.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).leftChild = nodeList
                .get(lastParentIndex * 2 + 1);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
    }

    /**
     * 先序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void preOrderTraverse(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * 中序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void inOrderTraverse(Node node) {
        if (node == null)
            return;
        inOrderTraverse(node.leftChild);
        System.out.print(node.data + " ");
        inOrderTraverse(node.rightChild);
    }

    /**
     * 后序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void postOrderTraverse(Node node) {
        if (node == null)
            return;
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data + " ");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    AlarmManager aManager;
    Calendar currentTime = Calendar.getInstance();



    public class BootCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "开机完了咯，好开心，别多玩哦", Toast.LENGTH_LONG).show();
        }
    }
}

