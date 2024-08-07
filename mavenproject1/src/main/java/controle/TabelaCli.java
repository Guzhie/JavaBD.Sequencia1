/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import conexao.Conexao;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableColumnModel;


public class TabelaCli extends JFrame{
    
        Conexao con_cliente;
    
        JLabel rCodigo, rNome, rEmail, rTel, rData;
        JTextField tCodigo, tNome, tEmail;
        JFormattedTextField tel, data;
        MaskFormatter mTel,Data;
        
        JTable tblClientes;
        JScrollPane scp_tabela;
        
        public TabelaCli(){
            con_cliente = new Conexao(); // inicialização do objeto
            con_cliente.conecta(); // chama o método que conecta           
            
            Container tela = getContentPane();
            setLayout(null);

            setTitle("Conexão de Java com MySql");
            setResizable(false);
         
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
            
                     
            rTel = new JLabel("Telefone:");
            rTel.setBounds(30, 110, 100, 20);
            rEmail = new JLabel("Email:");
            rEmail.setBounds(30, 140, 100, 20);
            
            tela.add(rCodigo);
            tela.add(tCodigo);
            tela.add(rNome);
            tela.add(tNome);
            tela.add(rData);
            tela.add(rTel);
            tela.add(rEmail);
            
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
        }
}
