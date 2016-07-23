/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Evaluation.CandidatePair;
import Evaluation.Evaluater;
import IR.Google;
import IR.Lucene;
import IR.QueryExtractor;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.filechooser.WebFileChooser;
import filesUtils.PhraseImporter;
import filesUtils.Source_docImporter;
import filesUtils.Suspicious_docImporter;
import filesUtils.TestPhraseImporter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;
import org.apache.commons.io.FileUtils;
import org.hibernate.mapping.Collection;

/**
 *
 * @author dali
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        webMenuBar2 = new com.alee.laf.menu.WebMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lbl_suspath = new javax.swing.JLabel();
        rb_web = new com.alee.laf.radiobutton.WebRadioButton();
        wb_local = new com.alee.laf.radiobutton.WebRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        List_sources = new com.alee.laf.list.WebList();
        webLabel1 = new com.alee.laf.label.WebLabel();
        ckbx_bag = new com.alee.laf.checkbox.WebCheckBox();
        ckbx_semantics = new com.alee.laf.checkbox.WebCheckBox();
        btn_run2 = new com.alee.laf.button.WebButton();
        ckbx_lex = new com.alee.laf.checkbox.WebCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btn_run = new com.alee.laf.button.WebButton();
        lbl_status = new com.alee.laf.label.WebLabel();
        webLabel4 = new com.alee.laf.label.WebLabel();
        webLabel5 = new com.alee.laf.label.WebLabel();
        cb_queryLength = new javax.swing.JComboBox();
        webMenuBar1 = new com.alee.laf.menu.WebMenuBar();
        jMenu1 = new javax.swing.JMenu();
        webMenuItem2 = new com.alee.laf.menu.WebMenuItem();
        webMenuItem1 = new com.alee.laf.menu.WebMenuItem();
        webMenu1 = new com.alee.laf.menu.WebMenu();
        webMenuItem3 = new com.alee.laf.menu.WebMenuItem();

        jMenu2.setText("File");
        webMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        webMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arabic Plagiarism Detector");
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setText("Suspicious file path:");

        jButton1.setText("Choose Suspicious file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_web);
        rb_web.setText("Web Search");

        buttonGroup1.add(wb_local);
        wb_local.setSelected(true);
        wb_local.setText("Local Search");

        List_sources.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(List_sources);

        webLabel1.setText("Plagiarism Suspected from:");

        ckbx_bag.setText("bag of words");

        ckbx_semantics.setText("Sematics");

        btn_run2.setText("Run phaseII");
        btn_run2.setEnabled(false);
        btn_run2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_run2ActionPerformed(evt);
            }
        });

        ckbx_lex.setText("Lexicals");

        btn_run.setText("Run phaseI");
        btn_run.setEnabled(false);
        btn_run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_runActionPerformed(evt);
            }
        });

        webLabel4.setText("Status:");

        webLabel5.setText("query length:");

        cb_queryLength.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        cb_queryLength.setSelectedItem(2);
        cb_queryLength.setToolTipText("");
        cb_queryLength.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(webLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_queryLength, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_run, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(webLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_run, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cb_queryLength, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(webLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(webLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jMenu1.setText("File");

        webMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        webMenuItem2.setText("Manage DB Files");
        webMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(webMenuItem2);

        webMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        webMenuItem1.setText("Exit");
        webMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(webMenuItem1);

        webMenuBar1.add(jMenu1);

        webMenu1.setText("About");

        webMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        webMenuItem3.setText("About us");
        webMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem3ActionPerformed(evt);
            }
        });
        webMenu1.add(webMenuItem3);

        webMenuBar1.add(webMenu1);

        setJMenuBar(webMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(rb_web, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(wb_local, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_suspath)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jSeparator1))
                            .addComponent(jSeparator2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(webLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_run2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(ckbx_lex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(ckbx_bag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(ckbx_semantics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_web, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(wb_local, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lbl_suspath))
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(webLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_run2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckbx_lex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckbx_bag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckbx_semantics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    String suspath = "";
    Suspicious_doc suspicious_doc = null;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        WebFileChooser chooser = new WebFileChooser();
        chooser.setCurrentDirectory(System.getProperty("user.home") + "\\Desktop");
        chooser.setDialogTitle("Choose file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showOpenDialog(new JFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            clearAllFileds();
            new Thread(new Runnable() {

                @Override
                public void run() {

                    lbl_status.setText("Processing suspicious file");
                    File selectedFile = chooser.getSelectedFile();
                    suspath = selectedFile.getAbsolutePath();
                    lbl_suspath.setText("folder: " + selectedFile.getName());
                    Suspicious_docImporter importer = new Suspicious_docImporter(suspath);
                    importer.import_();
                    importer.save();
                    String hsql = "from Suspicious_doc a order by a.suspicious_doc_id DESC ";
                    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();

                    suspicious_doc = (Suspicious_doc) session.createQuery(hsql).setMaxResults(1).list().get(0);
                    session.getTransaction().commit();

                    TestPhraseImporter tp = new TestPhraseImporter(suspicious_doc);
                    tp.import_();
                    tp.save();
                    session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    suspicious_doc = (Suspicious_doc) session.get(Suspicious_doc.class, suspicious_doc.getSuspicious_doc_id());
                    btn_run.setEnabled(true);

                    lbl_status.setText("Suspicious file processed");
                    if (!rb_web.isSelected()) {
                        cb_queryLength.setEnabled(true);
                    }
                }
            }).start();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearAllFileds() {
        cb_queryLength.setSelectedIndex(0);
        btn_run2.setEnabled(false);
        ckbx_bag.setSelected(false);
        ckbx_lex.setSelected(false);
        ckbx_semantics.setSelected(false);
        lbl_status.setText("pending");
        lbl_suspath.setText("");
        cb_queryLength.setEnabled(false);
        List_sources.removeAll();
        ListModel model = new AbstractListModel() {

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public Object getElementAt(int index) {
                return 0;
            }
        };
        List_sources.setModel(model);
    }
    List<Pair<String, String>> urlFiles = null;
    List<Source_doc> sources = null;
    private void btn_runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_runActionPerformed
        List_sources.removeAll();
        btn_run2.setEnabled(false);
        int queryLength = Integer.parseInt(cb_queryLength.getSelectedItem().toString());
        new Thread(new Runnable() {

            @Override
            public void run() {
                lbl_status.setText("Extracting queries");
                QueryExtractor extractor = new QueryExtractor(suspicious_doc);

                if (rb_web.isSelected()) //web search methodology 
                {
                    try {
                        File f = new File("D:\\googlefiles\\");
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        FileUtils.cleanDirectory(f);
                        Google g = new Google();
                        List<String> quList = extractor.extractAllSentence();
                        lbl_status.setText("Getting Urls from google");
                        urlFiles = new ArrayList();
                        Set<String> myUrls = new HashSet();
                        for (String q : quList) {
                            myUrls.addAll(g.getDataFromGoogle(q));

                        }

                        lbl_status.setText("Extracting data from Urls (this may take a while)");
                        urlFiles.addAll(g.getDatafromUrl(myUrls));
                        lbl_status.setText("Processing Data from Urls");
                        int length = f.listFiles().length;
                        Source_docImporter importer = new Source_docImporter("D:\\googlefiles\\");
                        importer.import_();
                        importer.save();

                        String hsql = "from Source_doc sources order by source_doc_id  DESC";
                        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                        Session session = sessionFactory.getCurrentSession();
                        session.beginTransaction();
                        List<Source_doc> sourc = (List<Source_doc>) session.createQuery(hsql).setMaxResults(length).list();;
                        session.getTransaction().commit();

                        PhraseImporter pimImporter;
                        for (Source_doc source : sourc) {
                            pimImporter = new PhraseImporter(source);
                            pimImporter.import_();
                            pimImporter.save();
                        }

                        hsql = "from Source_doc sources order by source_doc_id  DESC";
                        sessionFactory = HibernateUtil.getSessionFactory();
                        session = sessionFactory.getCurrentSession();
                        session.beginTransaction();
                        sources = (List<Source_doc>) session.createQuery(hsql).setMaxResults(length).list();;
                        session.getTransaction().commit();

                        lbl_status.setText("Viewing candidates");
                        ListModel model = new AbstractListModel() {

                            @Override
                            public int getSize() {
                                return urlFiles.size();
                            }

                            @Override
                            public Object getElementAt(int index) {
                                return Google.getDomainName(urlFiles.get(index).getKey());
                            }
                        };
                        List_sources.setModel(model);
                        List_sources.setSelectedIndex(0);
                        btn_run2.setEnabled(true);
                        lbl_status.setText("PhaseI Completed");

                    } catch (Exception ex) {
                        lbl_status.setText("Error cought, please restart operation");
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else // local search methodology
                {
                    List<String> quList = extractor.extractBygrams(queryLength);
                    lbl_status.setText("Searching for candidates");
                    StandardAnalyzer analyzer = new StandardAnalyzer();
                    Lucene indexer = new Lucene(analyzer);
                    HashSet<Integer> sourceIds = new HashSet<>();
                    for (String q : quList) {
                        sourceIds.addAll(indexer.searcher(indexer.getDir(), analyzer, q));
                    }
                    System.out.println(sourceIds);
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("idList", sourceIds);
                    String hsql = "from Source_doc sources where sources.source_doc_id IN (:idList)";
                    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    lbl_status.setText("Getting files from Database");
                    sources = (List<Source_doc>) session.createQuery(hsql).setParameterList("idList", sourceIds).list();;
                    session.getTransaction().commit();
                    lbl_status.setText("Viewing candidates");
                    ListModel model = new AbstractListModel() {

                        @Override
                        public int getSize() {
                            return sources.size();
                        }

                        @Override
                        public Object getElementAt(int index) {
                            return sources.get(index).getSource_doc_name();
                        }
                    };
                    List_sources.setModel(model);
                    List_sources.setSelectedIndex(0);
                    btn_run2.setEnabled(true);
                    lbl_status.setText("PhaseI Completed");
                }

            }
        }).start();


    }//GEN-LAST:event_btn_runActionPerformed

    private void btn_run2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_run2ActionPerformed
        // TODO add your handling code here:
        boolean bag = ckbx_bag.isSelected(), semantic = ckbx_semantics.isSelected(), lexicals = ckbx_lex.isSelected();

        new Thread(new Runnable() {

            @Override
            public void run() {

                if (rb_web.isSelected()) {
                    Source_doc source = null;
                    for (Source_doc doc : sources) {
                        if (doc.getSource_doc_name().equals(urlFiles.get(List_sources.getSelectedIndex()).getValue())) {
                            source = doc;
                            break;
                        }
                    }
                    if (source == null) {
                        lbl_status.setText("an error happend!!");
                        return;
                    }
                    lbl_status.setText("Evaluating palgiarism (this may taks a while)");
                    Evaluater evaluater = new Evaluater();
                    List<CandidatePair> candidatePairs = evaluater.getPlagirised(source, suspicious_doc, lexicals, bag, semantic);
                    if (candidatePairs.isEmpty()) {
                        lbl_status.setText("No plagiarism found!!");
                        return;
                    }
                    lbl_status.setText("Viewing plagiarism sentences");
                    List<Pair<String, String>> results = new ArrayList();
                    for (CandidatePair candidatePair : candidatePairs) {
                        results.add(new Pair(candidatePair.getPhrase().getOriginal(), candidatePair.getTestPhrase().getPhrase()));
//                        sources += candidatePair.getPhrase().getOriginal() + "\n ----------------\n";
//                        susps += candidatePair.getTestPhrase().getPhrase() + "\n ----------------\n";
                    }
                    ResultsForm res = new ResultsForm();

                    res.setVisible(true);
                    res.setUrlsite(urlFiles.get(List_sources.getSelectedIndex()).getKey());
                    res.viewResults(results);

                    lbl_status.setText("done");
                } else {
                    Source_doc source = sources.get(List_sources.getSelectedIndex());
                    lbl_status.setText("Evaluating palgiarism (this may taks a while)");
                    Evaluater evaluater = new Evaluater();
                    List<CandidatePair> candidatePairs = evaluater.getPlagirised(source, suspicious_doc, lexicals, bag, semantic);
                    if (candidatePairs.isEmpty()) {
                        lbl_status.setText("No plagiarism found!!");
                        return;
                    }
                    lbl_status.setText("Viewing plagiarism sentences");
                    List<Pair<String, String>> results = new ArrayList();
                    for (CandidatePair candidatePair : candidatePairs) {
                        results.add(new Pair(candidatePair.getPhrase().getOriginal(), candidatePair.getTestPhrase().getPhrase()));
//                        sources += candidatePair.getPhrase().getOriginal() + "\n ----------------\n";
//                        susps += candidatePair.getTestPhrase().getPhrase() + "\n ----------------\n";
                    }
                    ResultsForm res = new ResultsForm();
                    res.setVisible(true);
                    res.viewResults(results);
//                    txt_sources.setText(sources);
//                    txt_susps.setText(susps);
                    lbl_status.setText("done");
                }
            }
        }).start();

    }//GEN-LAST:event_btn_run2ActionPerformed

    private void webMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_webMenuItem1ActionPerformed

    private void webMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webMenuItem2ActionPerformed
        // TODO add your handling code here:
        ManagDB form = new ManagDB();
        form.setVisible(true);
    }//GEN-LAST:event_webMenuItem2ActionPerformed

    private void webMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webMenuItem3ActionPerformed
        // TODO add your handling code here:
        AboutusForm form = new AboutusForm();
        form.setVisible(true);
    }//GEN-LAST:event_webMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.list.WebList List_sources;
    private com.alee.laf.button.WebButton btn_run;
    private com.alee.laf.button.WebButton btn_run2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cb_queryLength;
    private com.alee.laf.checkbox.WebCheckBox ckbx_bag;
    private com.alee.laf.checkbox.WebCheckBox ckbx_lex;
    private com.alee.laf.checkbox.WebCheckBox ckbx_semantics;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.alee.laf.label.WebLabel lbl_status;
    private javax.swing.JLabel lbl_suspath;
    private com.alee.laf.radiobutton.WebRadioButton rb_web;
    private com.alee.laf.radiobutton.WebRadioButton wb_local;
    private com.alee.laf.label.WebLabel webLabel1;
    private com.alee.laf.label.WebLabel webLabel4;
    private com.alee.laf.label.WebLabel webLabel5;
    private com.alee.laf.menu.WebMenu webMenu1;
    private com.alee.laf.menu.WebMenuBar webMenuBar1;
    private com.alee.laf.menu.WebMenuBar webMenuBar2;
    private com.alee.laf.menu.WebMenuItem webMenuItem1;
    private com.alee.laf.menu.WebMenuItem webMenuItem2;
    private com.alee.laf.menu.WebMenuItem webMenuItem3;
    // End of variables declaration//GEN-END:variables
}
