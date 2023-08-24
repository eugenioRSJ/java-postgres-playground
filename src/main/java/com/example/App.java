package com.example;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import com.example.daos.ProductDao;
import com.example.models.Product;

public class App {
    private static final String PASSWORD = "";
    private static final String USERNAME = "gitpod";
    private static final String JDBC_URL = "jdbc:postgresql://localhost/postgres";

    public static void main(String[] args) {
        /*
         var product = new Product();
        product.setName("Eugenio aprovado em Primeiro");
        product.setPrice(15000);
        try {
            ProductDao dao = new ProductDao();
            //dao.Create(product);
            var products = dao.FindAll();
            products.stream().filter(p -> p.getPrice() > 13000).forEach(item -> System.out.println(item.getName()));
            //products.forEach(item -> System.out.println(item.getName()));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
         */
        int[] array = {10, 20, 50, 1, 5, 4, 6};
        //buble_sort(array);
        selection_sort(array);
        System.out.print(Arrays.toString(array));

        System.out.println(searchBinary(array, 90));
        
    }

    public static int searchBinary(int[] array, int target){
        if(array.length <= 0) return -1;
        int left = 0;
        int right =array.length -1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(array[mid] == target){
                return mid;
            } else if(target > array[mid]){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;

    }

    public static void selection_sort(int[] array){
        if(array.length <= 0) return;
        for(int i =0; i< array.length - 1; i++){
            var minValueIndice = i;
            for(int j=i+1; j < array.length; j++){
                if(array[minValueIndice] > array[j]){
                    minValueIndice = j;
                }
            }
            var temp = array[i];
            array[i] = array[minValueIndice];
            array[minValueIndice] = temp;
            System.out.println(Arrays.toString(array));
        }
    }
    public static void buble_sort(int[] array){
        if(array.length <= 0) return;
        for (int i = 0 ; i < array.length -1 ; i++) {
            boolean swap = false;
            for (int j = 0; j < array.length - i - 1; j++){
                if(array[j] > array[j+1]){
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                    swap = true;
                }
                System.out.println(Arrays.toString(array));
            }
            if(!swap){
                break;
            }
        } 
        System.out.print(Arrays.toString(array));
    }
    public App(){
        try(var conn = getConnection()){
            carregarDriverJDBC();
            listarEstados(conn);
            localizarEstado(conn, "PR");
            listarDadosTabela(conn, "produto");
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }        
    }

    private void listarDadosTabela(Connection conn, String tabela) {
        var sql = "select * from " + tabela;
        //System.out.println(sql);
        try {
            var statement = conn.createStatement();
            var result = statement.executeQuery(sql);

            var metadata = result.getMetaData();
            int cols = metadata.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                System.out.printf("%-25s | ", metadata.getColumnName(i));
            }
            System.out.println();

            while(result.next()){
                for (int i = 1; i <= cols; i++) {
                    System.out.printf("%-25s | ", result.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Erro na execução da consulta: " + e.getMessage());
        }
        
    }

    private void localizarEstado(Connection conn, String uf) {
        try{
            //var sql = "select * from estado where uf = '" + uf + "'"; //suscetível a SQL Injection
            var sql = "select * from estado where uf = ?";
            var statement = conn.prepareStatement(sql);
            //System.out.println(sql);
            statement.setString(1, uf);
            var result = statement.executeQuery();
            if(result.next()){
                System.out.printf("Id: %d Nome: %s UF: %s\n", result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch(SQLException e){
            System.err.println("Erro ao executar consulta SQL: " + e.getMessage());
        }
        
    }

    private void listarEstados(Connection conn) {
        try{
            System.out.println("Conexão com o banco realizada com sucesso.");

            var statement = conn.createStatement();
            var result = statement.executeQuery("select * from estado");
            while(result.next()){
                System.out.printf("Id: %d Nome: %s UF: %s\n", result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private void carregarDriverJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Não foi possível carregar a biblioteca para acesso ao banco de dados: " + e.getMessage());
        }
    }
}
