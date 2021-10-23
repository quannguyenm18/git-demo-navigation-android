package com.example.demonavigationdrawertoolbar.fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demonavigationdrawertoolbar.MainActivity;
import com.example.demonavigationdrawertoolbar.R;
import com.example.demonavigationdrawertoolbar.activitis.UpdateKhoanThuActivity;
import com.example.demonavigationdrawertoolbar.adapter.ListKTAdapter;
import com.example.demonavigationdrawertoolbar.database.DatabaseManager;
import com.example.demonavigationdrawertoolbar.interfaces.IClickItemKhoanThu;
import com.example.demonavigationdrawertoolbar.model.KhoanThu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentKhoanThu extends Fragment implements View.OnClickListener {
    EditText edt_nameKT, edt_money, edt_seacchKT;
    Button btn_nhap;
    RecyclerView rc_khoanthu;
    ListKTAdapter listKTAdapter;
    ArrayList<KhoanThu> khoanThuArrayList;
    ActivityResultLauncher<Intent> someActivityResult;
    TextView tv_delete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoanthu, container, false);
        anhxa(view);

        return view;
    }

    private void anhxa(View view) {
        edt_nameKT = view.findViewById(R.id.edt_nameKT);
        edt_money = view.findViewById(R.id.edt_money);
        btn_nhap = view.findViewById(R.id.btn_nhapKT);
        rc_khoanthu = view.findViewById(R.id.rc_KhoanThu);
        tv_delete = view.findViewById(R.id.tv_deleteALL);
        edt_seacchKT = view.findViewById(R.id.search_KhoanThu);


        khoanThuArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rc_khoanthu.setLayoutManager(linearLayoutManager);
        khoanThuArrayList = (ArrayList<KhoanThu>) DatabaseManager.getInstance(getContext()).khoanThuDAO().getArrayList();
        listKTAdapter = new ListKTAdapter(new IClickItemKhoanThu() {
            @Override
            public void OnClickItemKhoanThu(KhoanThu khoanThu) {
                OnclickItemKhoanThu(khoanThu);
            }

            @Override
            public void OnClickItemDeleteKT(KhoanThu khoanThu) {
                deleteItemKhoanThu(khoanThu);

            }

        });
        listKTAdapter.setData(khoanThuArrayList);
        rc_khoanthu.setAdapter(listKTAdapter);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xóa al;lll
                new AlertDialog.Builder(getContext())
                        .setTitle("con phơm  đe lít khoản thu ??")
                        .setMessage("tao sẽ xóa hết?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseManager.getInstance(getContext()).khoanThuDAO().deleteAlllKhoanThu();
                                Toast.makeText(getContext(), "Còn Cái Nịt", Toast.LENGTH_SHORT).show();
                                getDataFromDatabase();
                            }
                        }).setNegativeButton("no", null).show();
            }
        });
        edt_seacchKT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchKhoanThu();
                    return true;
                }
                return false;
            }
        });
        btn_nhap.setOnClickListener(this);
    }

    private void searchKhoanThu() {
        String strSeacrch = edt_seacchKT.getText().toString().trim();
        khoanThuArrayList = new ArrayList<>();
        khoanThuArrayList = (ArrayList<KhoanThu>) DatabaseManager
                .getInstance(getContext()).khoanThuDAO().searchKhoanThu(strSeacrch);
        listKTAdapter.setData(khoanThuArrayList);
        hideKeyboard(getActivity());


    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void deleteItemKhoanThu(KhoanThu khoanThu) {
        new AlertDialog.Builder(getContext())
                .setTitle("con phơm  đe lít khoản thu ??")
                .setMessage("Are You Sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance(getContext()).khoanThuDAO().DeleteKhoanThu(khoanThu);
                        Toast.makeText(getContext(), "Xóa xong rồi", Toast.LENGTH_SHORT).show();
                        getDataFromDatabase();
                    }
                }).setNegativeButton("no", null).show();


    }

    private void getDataFromDatabase() {
        khoanThuArrayList = (ArrayList<KhoanThu>) DatabaseManager.getInstance(getContext()).khoanThuDAO().getArrayList();
        listKTAdapter.setData(khoanThuArrayList);
        rc_khoanthu.setAdapter(listKTAdapter);
    }

    private void OnclickItemKhoanThu(KhoanThu khoanThu) {

        Intent intent = new Intent(getContext(), UpdateKhoanThuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJ", khoanThu);
        intent.putExtras(bundle);
        // code sự kiện on click khoan thu
        someActivityResult.launch(intent);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        someActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getDataFromDatabase();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        String nameKT = edt_nameKT.getText().toString().trim();
        String moneyKt = edt_money.getText().toString().trim();

        if (TextUtils.isEmpty(nameKT) && TextUtils.isEmpty(moneyKt)) {
            Toast.makeText(getContext(), "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        KhoanThu khoanThu = new KhoanThu(nameKT, Integer.parseInt(moneyKt));
        if (isKhoanThu(khoanThu) == true) {
            Toast.makeText(getContext(), "Tên Khoản Thu Đã Tồn Tại", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseManager.getInstance(getContext()).khoanThuDAO().insertKhoanThu(khoanThu);
        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
        getDataFromDatabase();
        clearText();


    }


    public void clearText() {
        edt_nameKT.setText("");
        edt_money.setText("");
    }

    public boolean isKhoanThu(KhoanThu khoanThu) {
        List<KhoanThu> khoanThuList = DatabaseManager.getInstance(getContext()).khoanThuDAO().checkListKT(khoanThu.getNameKT());
        if (khoanThuList != null && !khoanThuList.isEmpty()) {
            return true;
        }
        return false;
    }


}
