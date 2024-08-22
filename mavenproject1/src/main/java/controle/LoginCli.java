/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import conexao.Conexao;
import java.sql.*;
import java.sql.SQLException;
public class LoginCli extends JFrame{
    
    Conexao con_cliente;
    JPasswordField tSenha;
    JLabel rUsuario, rSenha, rTitulo, rAluno;
    JTextField tUsuario;
    JButton bLogar;
    
    public LoginCli(){
        
        con_cliente = new Conexao();
        con_cliente.conecta();
        
        setTitle("Login de Acesso");
        Container tela = getContentPane();
        setLayout(null);

        rAluno = new JLabel("Gustavo Henrique - 2°DS AMS - Tarde");
        rAluno.setBounds(20, 320, 400,10);

        rTitulo = new JLabel("Acceso ao Sistema");
        rTitulo.setBounds(210, 20, 150,50);

        rUsuario = new JLabel("Usuario: ");
        rUsuario.setBounds(150, 100, 100, 20);
        tUsuario = new JTextField(50);
        tUsuario.setBounds(210, 100, 150, 20);

        rSenha = new JLabel("Senha : ");
        rSenha.setBounds(150, 130, 100, 20);
        tSenha = new JPasswordField(50);
        tSenha.setBounds(210, 130, 150, 20);

        bLogar = new JButton("Login");
        bLogar.setBounds(215,180,100,20);
        bLogar.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                try {
                    String pesquisa = "select * from tblusuario where usuario like '" + tUsuario.getText() + "' && senha = " + tSenha.getText() + "";
                    con_cliente.executaSQL(pesquisa);

                    if (con_cliente.resultset.first()) {
                        TabelaCli mostra = new TabelaCli();
                           mostra.setVisible(true);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "\n Usuário não cadastrado!!!!","Mensagem do Programa" ,JOptionPane.INFORMATION_MESSAGE);
                        con_cliente.desconecta();
                        System.exit(0);
                    }
                } catch (SQLException errosql) {
                    JOptionPane.showMessageDialog(null,"\n Os dados digitados não foram localizados!! :\n "+errosql,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });


        tela.add(rTitulo);
        tela.add(rUsuario);
        tela.add(rSenha);
        tela.add(tUsuario);
        tela.add(tSenha);
        tela.add(bLogar);
        tela.add(rAluno);

        setSize(550, 400);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
