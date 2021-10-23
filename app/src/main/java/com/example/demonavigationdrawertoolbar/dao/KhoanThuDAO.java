package com.example.demonavigationdrawertoolbar.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demonavigationdrawertoolbar.model.KhoanThu;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface KhoanThuDAO {
    @Insert
    void insertKhoanThu(KhoanThu khoanThu);
    @Query("SELECT*FROM khoanthu")
    List<KhoanThu> getArrayList();
    @Query("SELECT*FROM khoanthu where NameKT = :nameKT")
    List<KhoanThu> checkListKT(String nameKT);
    @Update
    void  UpdateKhoanThu(KhoanThu khoanThu);
    @Delete
    void DeleteKhoanThu (KhoanThu khoanThu);
    @Query("DELETE FROM khoanthu")
    void  deleteAlllKhoanThu();
    @Query("SELECT*FROM Khoanthu where NameKT LIKE '%'|| :name|| '%'")
    List<KhoanThu> searchKhoanThu(String name);


}
