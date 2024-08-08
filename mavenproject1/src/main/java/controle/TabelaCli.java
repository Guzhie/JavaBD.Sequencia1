/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.sql.*;

import conexao.Conexao;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


public class TabelaCli extends JFrame{
    
        Conexao con_cliente;
    
        JLabel rCodigo, rNome, rEmail, rTel, rData;
        JTextField tCodigo, tNome, tEmail;
        JFormattedTextField tel, data;
        MaskFormatter mTel,mData;
        
        JTable tblClientes;
        JScrollPane scp_tabela;
        
        public TabelaCli(){
            con_cliente = new Conexao(); // inicialização do objeto
            con_cliente.conecta(); // chama o método que conecta           
            
            Container tela = getContentPane();
            setLayout(null);

            setTitle("Conexão de Java com MySql");
            setResizable(false);
         
            try{
                mData = new MaskFormatter("##/##/###");
                mTel = new MaskFormatter("(##)####-####");
                mData.setPlaceholderCharacter('_');
                mTel.setPlaceholderCharacter('_');

            } catch(ParseException excp){}    

            rCodigo = new JLabel("Codigo:");
            rCodigo.setBounds(30, 20, 100, 20);
            tCodigo = new JTextField(50);
            tCodigo.setBounds(120, 20, 200, 25);
        
        
            rNome = new JLabel("Nome:");
            rNome.setBounds(30, 50, 100, 20);
            tNome = new JTextField(50);
            tNome.setBounds(120, 50, 200, 25);
            
            rData = new JLabel("Data de Nascimento:");
            rData.setBounds(30, 80, 150, 20);
            data = new JFormattedTextField(mData);
            data.setBounds(150, 80, 150, 20);
            
            rTel = new JLabel("Telefone:");
            rTel.setBounds(30, 110, 100, 20);
            tel = new JFormattedTextField(mTel);
            tel.setBounds(120, 110, 150, 20);

            
            
            rEmail = new JLabel("Email:");
            rEmail.setBounds(30, 140, 100, 20);
            tEmail = new JTextField(50);
            tEmail.setBounds(120, 140, 200, 25);
            
            tela.add(rCodigo);
            tela.add(tCodigo);
            tela.add(rNome);
            tela.add(tNome);
            tela.add(rData);
            tela.add(data);
            tela.add(rTel);
            tela.add(tel);
            tela.add(rEmail);
            tela.add(tEmail);
            
            tblClientes = new javax.swing.JTable();
            scp_tabela = new javax.swing.JScrollPane();
            
            tblClientes.setBounds(50, 200,550, 200);
            scp_tabela.setBounds(50, 200,550, 200);
            
            tela.add(tblClientes);
            tela.add(scp_tabela);
            
            tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
            
            tblClientes.setFont(new java.awt.Font("Arial", 1, 12));
            
            tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
            },
            new String[] {"Codigo", "Nome", "Data Nascimento", "Telefone", "Email"})
            {
            boolean[] canEdit = new boolean[]{false,false,false,false,false};
            
            public boolean isCellEditable(int rowIndex, int columnIndex){
            return canEdit[columnIndex];}
            }
            );
            scp_tabela.setViewportView(tblClientes);
            
            tblClientes.setAutoCreateRowSorter(true);
            
            setSize(800,600);
            setVisible(true);
            setLocationRelativeTo(null);
            
            con_cliente.executaSQL("select * from tbclientes order by cod");
            preencherTabela();
        }
        

        public void preencherTabela() {
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(4);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(11);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(14);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(100);

        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setNumRows(0);
            
        try {
            con_cliente.resultset.beforeFirst();
            while (con_cliente.resultset.next()) {
                modelo.addRow(new Object[]{
                    con_cliente.resultset.getString("cod"),
                    con_cliente.resultset.getString("nome"),
                    con_cliente.resultset.getString("dt_nasc"),
                    con_cliente.resultset.getString("telefone"),
                    con_cliente.resultset.getString("email")
                });
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar dados da tabela!! :\n" + erro, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

        
}
