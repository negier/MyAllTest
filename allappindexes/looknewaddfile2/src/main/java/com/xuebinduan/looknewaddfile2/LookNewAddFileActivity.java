package com.xuebinduan.looknewaddfile2;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 查看公有空间新增文件。为了白嫖软件，奥利给。
 * TODO
 * 我发现一个BUG,在OPPO手机上发现的，但应该不是手机的问题。就是有些文件扫不到，扫描算法没有问题，我指定那个目录，
 * 结果listFiles()还是没有它，所以我知道这里不是我的问题了，系统问题。
 * 我将targetSdk从30改为29，然后问题解决了，所有文件都扫得到了。真的很骂娘，浪费我宝贵的两个小时，结果只用改个数字。
 */
public class LookNewAddFileActivity extends AppCompatActivity {

    private HashSet<String> lockFiles = new HashSet<>();
    private File rootFile = Environment.getExternalStorageDirectory();
    private HashSet<String> newAddedFiles = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_new_add_file);

        setTitle("查看公区文件新增");

        /**
         * 思路：
         * 先Lock扫描文件存为hashmap，下次进去的时候再比对就能把哪些是新增的找出来。
         */

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = openFileInput("lockFiles.set");
            objectInputStream = new ObjectInputStream(fileInputStream);
            lockFiles = (HashSet<String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (!lockFiles.isEmpty()) {
            ((TextView) findViewById(R.id.lock)).setText("Lock Last Saved");
        }
        //扫描当前所有的文件信息保存到本地，等查看新增时进行比对。
        findViewById(R.id.lock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockFiles.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        scanFileToLocalHashMapFile();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LookNewAddFileActivity.this, "Lock完成", Toast.LENGTH_SHORT).show();
                                ((TextView) view).setText("LOCK 完成");
                            }
                        });
                    }
                }).start();
            }
        });

        //查看新增
        findViewById(R.id.look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAddedFiles.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        lookoutNewAddedFile();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showNewAddedFileMsg();
                            }
                        });
                    }
                }).start();

            }
        });

    }

    private void showNewAddedFileMsg() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        Log.e("TAG", "=============================新增=========================");
        List<String> list = new ArrayList<>();
        for (String string : newAddedFiles) {
            Log.e("TAG", "文件：" + string);
            list.add(string);
        }
        Log.e("TAG", "新增文件：" + newAddedFiles.size());
        adapter.setData(list);
        Toast.makeText(LookNewAddFileActivity.this, "查看新增结束", Toast.LENGTH_LONG).show();
    }

    private void lookoutNewAddedFile() {
        scanFile(rootFile, true);
    }

    private void scanFileToLocalHashMapFile() {
        scanFile(rootFile,false);

        for (String string : lockFiles) {
            Log.e("TAG", "文件：" + string);
        }
        Log.e("TAG", "扫描到的文件总数：" + lockFiles.size());
        //保存到本地
        saveObjectToLocalFile(lockFiles);
    }

    private void scanFile(File file, boolean isLookNewFile) {
        //Android文件夹不扫描
        if (file.getAbsolutePath().startsWith(rootFile.getAbsolutePath() + File.separator + "Android")) {
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    scanFile(f, isLookNewFile);
                }
            }
        }

        //文件和文件夹路径都添加进去
        if (isLookNewFile) {
            if (!lockFiles.contains(file.getAbsolutePath())) {
                newAddedFiles.add(file.getAbsolutePath());
            }
        } else {
            lockFiles.add(file.getAbsolutePath());
        }
    }

    private void scanFile2(File root) {
        Queue<File> queue = new LinkedList<File>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            while (count > 0) {
                File node = queue.poll();
                File[] files = node.listFiles();
                //层序遍历
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            queue.add(file);
                        }
                        Log.e("TAGGGGG", "文件：" + file.getAbsolutePath());
                    }
                }
                count--;
            }
        }
    }

    public void saveObjectToLocalFile(Object obj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //通过openFileOutput方法得到一个输出流
            fos = openFileOutput("lockFiles.set", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj); //写入
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close(); //最后关闭输出流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}