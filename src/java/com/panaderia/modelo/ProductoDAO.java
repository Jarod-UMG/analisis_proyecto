/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panaderia.modelo;

import com.panaderia.config.Conexion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JAROD
 */
public class ProductoDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Producto>productos = new ArrayList();
        String sql = "SELECT *FROM productos";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                productos.add(p);
            }
        }catch(Exception e){
        }
        return productos;
    }
    
    public void listarimg(int id, HttpServletResponse response){
        String sql = "SELECT *FROM productos WHERE id_producto =" + id;
        InputStream inputstream = null;
        OutputStream outputstream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            outputstream = response.getOutputStream();
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                inputstream = rs.getBinaryStream("foto");
                
            }
                bufferedInputStream = new BufferedInputStream(inputstream);
                bufferedOutputStream = new BufferedOutputStream(outputstream);
                int i=0;
                while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
                
            }
        } catch (Exception e) {
        }
    }
}
