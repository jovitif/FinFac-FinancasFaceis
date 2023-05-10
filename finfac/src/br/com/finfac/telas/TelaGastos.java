/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import br.com.finfac.dao.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author João Sales
 */
public class TelaGastos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaGastos
     */
    public TelaGastos() {
        getContentPane().setBackground(Color.WHITE);
        initComponents();
        conexao = ModuloConexao.conectar();
        pesquisar_evento();
    }

    private void pesquisar_evento() {
        String sql = "select idevento as Id, descricao as Descrição from tbeventos where descricao like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%" + txtEvento.getText() + "%");
            rs = pst.executeQuery();
            tblEventos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setar_campos() {
        int setar = tblEventos.getSelectedRow();
        txtId.setText(tblEventos.getModel().getValueAt(setar, 0).toString());
    }

    private void cadastrar() {
        String sql = "insert into tbgastosevento(datagasto, horagasto, categoria, descricao, valor, meiopagamento, observacoes, idevento, situacao) values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtData.getText());
            pst.setString(2, txtHorario.getText());
            pst.setString(3, cboCategoria.getSelectedItem().toString());
            pst.setString(4, txtDescricao.getText());
            pst.setString(5, txtValor.getText().replace(",", "."));
            pst.setString(6, cboMeio.getSelectedItem().toString());
            pst.setString(7, txtObservacoes.getText());
            pst.setString(8, txtId.getText());
            pst.setString(9, cboSituacao.getSelectedItem().toString());
            if ((txtId.getText().isEmpty()) || (txtValor.getText().isEmpty()) || cboCategoria.getSelectedItem().equals(" ") || cboMeio.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Gasto adicionado com sucesso");
                    //    btnAdicionar.setEnabled(false);
                    //     btnPesquisar.setEnabled(false);
                    limpar();
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtGasto.setText(null);
        txtDataCadastro.setText(null);
        cboCategoria.setSelectedItem(" ");
        cboMeio.setSelectedItem(" ");
        cboSituacao.setSelectedItem(0);
        txtData.setText(null);
        txtHorario.setText(null);
        txtDescricao.setText(null);
        txtValor.setText(null);
        txtObservacoes.setText(null);
        txtId.setText(null);
        ((DefaultTableModel) tblEventos.getModel()).setRowCount(0);
        btnAdicionar.setEnabled(true);
        btnPesquisar.setEnabled(true);
        tblEventos.setEnabled(true);
        btnEditar.setEnabled(false);
        btnDeletar.setEnabled(false);
    }

    private void pesquisar() {
        String numero = JOptionPane.showInputDialog("Número do Gasto");
        String sql = "select * from tbgastosevento where idgasto = " + numero;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtGasto.setText(rs.getString(1));
                txtDataCadastro.setText(rs.getString(2));
                txtData.setText(rs.getString(3));
                txtHorario.setText(rs.getString(4));
                cboCategoria.setSelectedItem(rs.getString(5));
                txtDescricao.setText(rs.getString(6));
                txtValor.setText(rs.getString(7));
                cboMeio.setSelectedItem(rs.getString(8));
                txtObservacoes.setText(rs.getString(9));
                txtId.setText(rs.getString(10));
                cboSituacao.setSelectedItem(rs.getString(11));
                //evitando problemas
                btnAdicionar.setEnabled(false);
                txtEvento.setEnabled(false);
                btnPesquisar.setEnabled(false);
                tblEventos.setVisible(false);
                btnEditar.setEnabled(true);
                btnDeletar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Gasto não cadastrado");
            }
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Gasto invalido");
            System.out.println(e);
        } catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private void alterar() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma as alterações nos gastos deste evento ?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "update tbgastosevento set datagasto=?,horagasto=?,categoria=?,descricao=?,valor=?,meiopagamento=?,observacoes=?,situacao=? where idgasto=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtData.getText());
                pst.setString(2, txtHorario.getText());
                pst.setString(3, cboCategoria.getSelectedItem().toString());
                pst.setString(4, txtDescricao.getText());
                pst.setString(5, txtValor.getText());
                pst.setString(6, cboMeio.getSelectedItem().toString());
                pst.setString(7, txtObservacoes.getText());
                pst.setString(8, cboSituacao.getSelectedItem().toString());
                pst.setString(9, txtGasto.getText());
                if ((txtId.getText().isEmpty()) || (txtValor.getText().isEmpty()) || cboCategoria.getSelectedItem().equals(" ") || cboMeio.getSelectedItem().equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Gasto atualizado com sucesso");

                        limpar();
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse gasto ?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbgastosevento where idgasto=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtGasto.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Gasto excluido com sucesso");
                    limpar();
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtGasto = new javax.swing.JTextField();
        txtHorario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboSituacao = new javax.swing.JComboBox<>();
        txtDataCadastro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtEvento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEventos = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        cboMeio = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();

        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gastos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Situação");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 529, -1, -1));

        jButton6.setText("Imprimir");
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1087, 501, -1, -1));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("N gasto");

        txtGasto.setEditable(false);
        txtGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGastoActionPerformed(evt);
            }
        });

        txtHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorarioActionPerformed(evt);
            }
        });

        jLabel2.setText("Data");

        jLabel4.setText("Horario");

        jLabel5.setText("Situação");

        cboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em observação", "Aprovado", "Recusado" }));
        cboSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSituacaoActionPerformed(evt);
            }
        });

        txtDataCadastro.setEditable(false);
        txtDataCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataCadastroActionPerformed(evt);
            }
        });

        jLabel12.setText("Data cadastro");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(34, 34, 34)
                        .addComponent(txtHorario)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 470, 180));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Evento"));

        txtEvento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEventoKeyReleased(evt);
            }
        });

        jLabel6.setText("*ID");

        txtId.setEditable(false);

        tblEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Evento", "Descrição"
            }
        ));
        tblEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEventosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEventos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtId)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 500, 260));

        btnAdicionar.setBackground(new java.awt.Color(78, 92, 188));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/add.png"))); // NOI18N
        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 420, -1, -1));

        btnPesquisar.setBackground(new java.awt.Color(78, 92, 188));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/search.png"))); // NOI18N
        btnPesquisar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        getContentPane().add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, -1, -1));

        btnEditar.setBackground(new java.awt.Color(78, 92, 188));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/editar.png"))); // NOI18N
        btnEditar.setEnabled(false);
        btnEditar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 420, -1, -1));

        btnDeletar.setForeground(new java.awt.Color(78, 92, 188));
        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/trash.png"))); // NOI18N
        btnDeletar.setEnabled(false);
        btnDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, -1, -1));

        jLabel7.setText("Meio de Pagamento");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, -1, -1));

        txtObservacoes.setColumns(20);
        txtObservacoes.setRows(5);
        jScrollPane2.setViewportView(txtObservacoes);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 490, 100));

        jLabel8.setText("Observações");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, -1, -1));

        cboMeio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Dinheiro", "Cartão de crédito", "Cartão de débito", "Transferência bancária", "Boleto bancário", "Cheque", "Pix", "PayPal", "PicPay", "Mercado Pago", "Outro " }));
        cboMeio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMeioActionPerformed(evt);
            }
        });
        getContentPane().add(cboMeio, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 240, 130, -1));

        jLabel9.setText("Categoria");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        txtValor.setText("0.0");
        getContentPane().add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, 130, -1));

        jLabel10.setText("Valor");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, -1, -1));

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Alimentação", "Transporte", "Habitação", "Educação", "Saúde", "Lazer", "Vestuário", "Eletrônicos", "Utilidades domésticas", "Impostos e taxas", "Presentes", "Outros" }));
        getContentPane().add(cboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        jLabel11.setText("Descrição");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane3.setViewportView(txtDescricao);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 296, 460, 100));

        setBounds(0, 0, 1074, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void txtGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGastoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGastoActionPerformed

    private void cboSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSituacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSituacaoActionPerformed

    private void cboMeioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMeioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMeioActionPerformed

    private void txtEventoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEventoKeyReleased
        pesquisar_evento();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventoKeyReleased

    private void tblEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEventosMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblEventosMouseClicked

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        cadastrar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtDataCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataCadastroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataCadastroActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void txtHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JComboBox<String> cboMeio;
    public static javax.swing.JComboBox<String> cboSituacao;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblEventos;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDataCadastro;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtEvento;
    private javax.swing.JTextField txtGasto;
    private javax.swing.JTextField txtHorario;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextArea txtObservacoes;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
