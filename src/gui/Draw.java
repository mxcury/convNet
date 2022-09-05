package gui;

import io.*;

import network.*;
import network.grid.DenseGrid;
import network.grid.Grid;
import network.loss.MSE;
import network.optimiser.ADAM;
import network.optimiser.GD;
import network.optimiser.Optimiser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class Draw extends javax.swing.JFrame {
    Network net = null;
    private javax.swing.JProgressBar[] progressBars = new javax.swing.JProgressBar[62];
    public EMnistDataReader reader = null;
    private static final int BATCH_SIZE = 16;
    private static final int EPOCH_ITERATIONS = 10000;
    private Boolean process = Boolean.TRUE;
    private BufferedImage OptimalInimg = null;


    public Draw() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Draw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Draw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Draw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Draw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();

        this.addMouseListener(DrawPanel);
        this.addMouseMotionListener(DrawPanel);


        double[] randomInitValuesForTom = new double[62];
        for(int i = 0; i < randomInitValuesForTom.length; i++){
            randomInitValuesForTom[i] = Math.random();
        }
        this.updateOutputs(randomInitValuesForTom);

        ClearActionPerformed(null);

        updateOverview();

    }

    private void initComponents() {

        MainUI = new javax.swing.JTabbedPane();
        DrawWindow = new javax.swing.JPanel();
        DrawGroupPanel = new javax.swing.JPanel();
        DrawPanel = new DrawPanel();
        GoButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        ComputedPanel = new javax.swing.JPanel();
        DataLossLabel = new javax.swing.JLabel();
        ProbabilitiesPanel = new javax.swing.JPanel();
        OverviewWindow = new javax.swing.JPanel();
        OverviewPane = new javax.swing.JSplitPane();
        NetworkImage = new NetworkImageOverview();
        ScrollNetOverview = new javax.swing.JScrollPane();
        TextOverview = new javax.swing.JTextArea();
        TrainingWindow = new javax.swing.JPanel();
        TrainButton = new javax.swing.JButton();
        DataLoadButton = new javax.swing.JButton();
        NetworkLoadButton = new javax.swing.JButton();
        NetworkSaveButton = new javax.swing.JButton();
        EpochNumber = new javax.swing.JLabel();
        TrainingProgress = new javax.swing.JProgressBar();
        DataPicture = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                if(DataPicture.isVisible() && DataPicture.isShowing()){
                    super.paintComponent(g);
                    BufferedImage img = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
                    g.drawImage(img,0, 0, this.getWidth(), this.getWidth(), this);
                }
            }
        };
        DataLabel = new javax.swing.JLabel();
        StopButton = new javax.swing.JButton();
        Error = new javax.swing.JLabel();
        DataWindow = new javax.swing.JPanel();
        LossGraph = new LossGraph();
        LossLabel = new javax.swing.JLabel();
        ValidationLabel = new javax.swing.JLabel();
        OptimalInput = new javax.swing.JButton();
        NetworkCreate = new javax.swing.JPanel();
        OptiInImage = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                if (OptimalInimg == null){
                    return;
                }
                super.paintComponent(g);
//                System.out.println("width"+OptiInImage.getWidth());
                g.drawImage(OptimalInimg,0,0,128,128,this);
            }
        };

        OptiInLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        okErrorButton = new javax.swing.JButton();
        AddButton = new javax.swing.JButton();
        CreateButton = new javax.swing.JButton();
        LayerTitle = new javax.swing.JLabel();
        LayerInfo = new javax.swing.JTextField();
        LayerSettings = new javax.swing.JTextField();
        LayerImage = new javax.swing.JPanel();
        LayerImageScroll = new javax.swing.JScrollBar();
        for(int i=0;i<progressBars.length;i++){
            progressBars[i] = new javax.swing.JProgressBar();
        }
        this.setPreferredSize(new Dimension(1500, 1000));
        this.setResizable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        DrawWindow.setLayout(new java.awt.GridLayout(1, 3));

        DrawGroupPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("DrawPanel"));

        DrawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        DrawPanel.setMaximumSize(new Dimension(464, 464));
        DrawPanel.setMinimumSize(new Dimension(464, 464));
        DrawPanel.setPreferredSize(new Dimension(464, 464));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(DrawPanel);
        DrawPanel.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 462, Short.MAX_VALUE)
        );

        GoButton.setText("Go");
        GoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComputeActionPerformed(evt);
            }
        });
        DrawGroupPanel.add(GoButton);

        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });
        DrawGroupPanel.add(ClearButton);

        javax.swing.GroupLayout DrawGroupPanelLayout = new javax.swing.GroupLayout(DrawGroupPanel);
        DrawGroupPanel.setLayout(DrawGroupPanelLayout);
        DrawGroupPanelLayout.setHorizontalGroup(
            DrawGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrawGroupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DrawGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DrawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DrawGroupPanelLayout.createSequentialGroup()
                        .addComponent(GoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        DrawGroupPanelLayout.setVerticalGroup(
            DrawGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrawGroupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DrawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrawGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GoButton)
                    .addComponent(ClearButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        DrawWindow.add(DrawGroupPanel);

        ComputedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("ComputedOutput"));

        DataLossLabel.setFont(new java.awt.Font("Dialog", 1, 100)); // NOI18N
        DataLossLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DataLossLabel.setText(" ");
        DataLossLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout ComputedPanelLayout = new javax.swing.GroupLayout(ComputedPanel);
        ComputedPanel.setLayout(ComputedPanelLayout);
        ComputedPanelLayout.setHorizontalGroup(
            ComputedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DataLossLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ComputedPanelLayout.setVerticalGroup(
            ComputedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DataLossLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );

        DrawWindow.add(ComputedPanel);

        ProbabilitiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Probabilities"));

        javax.swing.GroupLayout ProbabilitiesPanelLayout = new javax.swing.GroupLayout(ProbabilitiesPanel);
        ProbabilitiesPanel.setLayout(ProbabilitiesPanelLayout);
        ProbabilitiesPanelLayout.setHorizontalGroup(
            ProbabilitiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        ProbabilitiesPanelLayout.setVerticalGroup(
            ProbabilitiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );

        GridLayout experimentLayout = new GridLayout(62,2);
        ProbabilitiesPanel.setLayout(experimentLayout);
        for(int i =0;i<62;i++){
            JLabel l = new JLabel(Classes.values()[i].name());
            l.setForeground(Color.blue);
            l.setPreferredSize(new Dimension(50,10));
            ProbabilitiesPanel.add(l);
            ProbabilitiesPanel.add(progressBars[i]);
        }

        getContentPane().add(ProbabilitiesPanel);

        DrawWindow.add(ProbabilitiesPanel);

        MainUI.addTab("Draw", DrawWindow);

        OverviewPane.setDividerLocation(400);
        OverviewPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout NetworkImageLayout = new javax.swing.GroupLayout(NetworkImage);
        NetworkImage.setLayout(NetworkImageLayout);
        NetworkImageLayout.setHorizontalGroup(
            NetworkImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );
        NetworkImageLayout.setVerticalGroup(
            NetworkImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        OverviewPane.setLeftComponent(NetworkImage);

        ScrollNetOverview.setEnabled(false);

        TextOverview.setEditable(false);
        TextOverview.setColumns(20);
        TextOverview.setRows(5);
        ScrollNetOverview.setViewportView(TextOverview);

        OverviewPane.setRightComponent(ScrollNetOverview);

        javax.swing.GroupLayout OverviewWindowLayout = new javax.swing.GroupLayout(OverviewWindow);
        OverviewWindow.setLayout(OverviewWindowLayout);
        OverviewWindowLayout.setHorizontalGroup(
            OverviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OverviewWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OverviewPane)
                .addContainerGap())
        );
        OverviewWindowLayout.setVerticalGroup(
            OverviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OverviewWindowLayout.createSequentialGroup()
                .addComponent(OverviewPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainUI.addTab("Overview", OverviewWindow);

        DataLoadButton.setText("Load Data");
        DataLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadDataActionPerformed(evt);
            }
        });


        NetworkLoadButton.setText("Load Network");
        NetworkLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadNetworkActionPerformed(evt);
            }
        });

        NetworkSaveButton.setText("Save Network");
        NetworkSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveNetworkActionPerformed(evt);
            }
        });

        EpochNumber.setText("Epoch: ");

        javax.swing.GroupLayout DataPictureLayout = new javax.swing.GroupLayout(DataPicture);
        DataPicture.setLayout(DataPictureLayout);
        DataPictureLayout.setHorizontalGroup(
            DataPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );
        DataPictureLayout.setVerticalGroup(
            DataPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );



        TrainButton.setText("Train");
        TrainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrainActionPerformed(evt);
            }
        });

        DataLabel.setFont(new java.awt.Font("Dialog", 1, 100)); // NOI18N
        DataLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DataLabel.setText(" ");
        DataLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        StopButton.setText("Stop ");
        StopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopButtonActionPerformed(evt);
            }
        });

        Error.setText(" ");

        javax.swing.GroupLayout TrainingWindowLayout = new javax.swing.GroupLayout(TrainingWindow);
        TrainingWindow.setLayout(TrainingWindowLayout);
        TrainingWindowLayout.setHorizontalGroup(
                TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TrainingProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                                .addComponent(DataPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(DataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                                .addComponent(StopButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(TrainButton))
                                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(EpochNumber)
                                                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                                                .addComponent(DataLoadButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(NetworkLoadButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(NetworkSaveButton)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(Error)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        TrainingWindowLayout.setVerticalGroup(
                TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TrainingWindowLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(TrainButton)
                                                        .addComponent(StopButton)))
                                        .addGroup(TrainingWindowLayout.createSequentialGroup()
                                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(DataLoadButton)
                                                        .addComponent(NetworkLoadButton)
                                                        .addComponent(NetworkSaveButton)
                                                        .addComponent(Error))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(EpochNumber)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(TrainingProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(TrainingWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(DataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                                        .addComponent(DataPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );


        MainUI.addTab("Training", TrainingWindow);

        javax.swing.GroupLayout LossGraphLayout = new javax.swing.GroupLayout(LossGraph);
        LossGraph.setLayout(LossGraphLayout);
        LossGraphLayout.setHorizontalGroup(
            LossGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        LossGraphLayout.setVerticalGroup(
            LossGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        LossLabel.setText("Loss");

        ValidationLabel.setText("Current Validation: ");

        OptimalInput.setText("Optimal Input");
        OptimalInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindOptimalInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OptiInImageLayout = new javax.swing.GroupLayout(OptiInImage);
        OptiInImage.setLayout(OptiInImageLayout);
//        OptiInImage.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
//        OptiInImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Optimal Input"));
        OptiInImageLayout.setHorizontalGroup(
                OptiInImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );
        OptiInImageLayout.setVerticalGroup(
                OptiInImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );

        OptiInLabel.setText(" ");

        javax.swing.GroupLayout DataWindowLayout = new javax.swing.GroupLayout(DataWindow);
        DataWindow.setLayout(DataWindowLayout);
        DataWindowLayout.setHorizontalGroup(
                DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DataWindowLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(DataWindowLayout.createSequentialGroup()
                                                .addComponent(LossGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ValidationLabel))
                                        .addComponent(LossLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                .addGroup(DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(OptiInImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(OptimalInput)
                                        .addComponent(OptiInLabel))
                                .addContainerGap())
        );
        DataWindowLayout.setVerticalGroup(
                DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DataWindowLayout.createSequentialGroup()
                                .addGroup(DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(DataWindowLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(LossGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(DataWindowLayout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(ValidationLabel))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DataWindowLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(OptiInImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DataWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LossLabel)
                                        .addComponent(OptiInLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addComponent(OptimalInput)
                                .addContainerGap())
        );

        MainUI.addTab("Data", DataWindow);

        AddButton.setText("Add Layer");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });


        CreateButton.setText("Create Network");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        LayerTitle.setText("Selected Layer:-");

        LayerInfo.setText("Layer Info:");

        LayerSettings.setText("Edit Layer Info");

        javax.swing.GroupLayout LayerImageLayout = new javax.swing.GroupLayout(LayerImage);
        LayerImage.setLayout(LayerImageLayout);
        LayerImageLayout.setHorizontalGroup(
                LayerImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        LayerImageLayout.setVerticalGroup(
                LayerImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout NetworkCreateLayout = new javax.swing.GroupLayout(NetworkCreate);
        NetworkCreate.setLayout(NetworkCreateLayout);
        NetworkCreateLayout.setHorizontalGroup(
                NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                .addComponent(LayerImageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(CreateButton))
                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                                .addComponent(AddButton)
                                                                .addGap(151, 151, 151))
                                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                                .addComponent(LayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(8, 8, 8)))
                                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(LayerTitle)
                                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                                .addComponent(LayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LayerSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))))
                                .addContainerGap())
        );
        NetworkCreateLayout.setVerticalGroup(
                NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(AddButton)
                                        .addComponent(LayerTitle))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(NetworkCreateLayout.createSequentialGroup()
                                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(LayerSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                                        .addComponent(LayerInfo))
                                                .addGap(0, 5, Short.MAX_VALUE))
                                        .addComponent(LayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NetworkCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(CreateButton)
                                        .addComponent(LayerImageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        MainUI.addTab("Create", NetworkCreate);

        getContentPane().add(MainUI);

        pack();


    }

    private void StopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        process=Boolean.FALSE;
//        System.out.println("process set to Boolean.FALSE");
    }

    private void CreateActionPerformed(java.awt.event.ActionEvent evt){

    }

    private void AddActionPerformed(java.awt.event.ActionEvent evt){

    }

    private void FindOptimalInputActionPerformed(java.awt.event.ActionEvent evt){
        if(net==null){
            errorOccured("No Network loaded");
            return;
        }
        //TODO this needs to selected from the user

        String input = displayUserInput();
        System.out.println(input);

        int in = valueOfInput(input);



        DenseGrid expectedOutput = new DenseGrid(62);
        expectedOutput.set(in,1);

        net.getInputLayer().setOutput(new DenseGrid(128, 128,1,true));
        Grid inputTarget = net.getInputLayer().getOutput();
        for(int i = 0; i < inputTarget.size(); i++){
            inputTarget.set(i,Math.random());
        }

//        ADAM optimiser = new ADAM(0.001);
        GD optimiser = new GD(1000);
        ArrayList<Grid> optimiserGrids = new ArrayList<>();
        optimiserGrids.add(inputTarget);
        optimiser.init(optimiserGrids);

        for(int i =0; i < 10; i++){
            net.compute(inputTarget);
            double loss = net.computeLoss(expectedOutput);
//            if(i % 100 == 0)
                System.out.println(loss);
            net.backprop();

            optimiser.optimise();
        }

//
        inputTarget.normalise();
        OptimalInimg = ImageLoader.getScaledImage(ImageLoader.tensorToImage(inputTarget,ImageLoader.NORMALISE),256,256);
//        ImageLoader.displayImage(ImageLoader.getScaledImage(ImageLoader.tensorToImage(inputTarget,ImageLoader.NORMALISE),256,256));
        updateOptiInLabel(input);


//

    }
    private void errorOccured(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Whoops :/",
                JOptionPane.ERROR_MESSAGE);
    }
    private String displayUserInput() {
        String s = (String) JOptionPane.showInputDialog(new JFrame(),"Enter the value to find the optimal input ",
                "Optimal Input",JOptionPane.PLAIN_MESSAGE);
        return s;
    }


    private void networkLoaded(String message){
        JOptionPane.showMessageDialog(new JFrame(), message+" successfully loaded");
    }

    private int valueOfInput(String input){
        char c = input.charAt(0);
            for(int i=0;i<=Classes.values().length;i++){
                if(c==Classes.values()[i].getMeaning()){
                    return i;
                }
            }
            return Integer.parseInt(null);
    }




    private void TrainActionPerformed(java.awt.event.ActionEvent evt) {
        if(net==null){
            errorOccured("No Network loaded");
            return;
        }
        if(reader==null){
            errorOccured("No Data loaded");
            return;
        }
        new Thread(){
            public void run(){
                process = Boolean.TRUE;
                    net.prepareTraining();
//                    long time = System.nanoTime();
                    Graphics graphics = DataPicture.getGraphics();


                    for(int i=0;i<EPOCH_ITERATIONS;i++){
//                        double loss=0;
                        double batchLoss = 0;
                        int batchEntryCount = 0;
                        for(int n=0;n<reader.getInputs().size();n++){
                            if(process==Boolean.FALSE){
                                updateTrainingPictures(n,graphics);
                                LossGraph.repaint();
                                return;
                            }
                            net.compute(reader.getInputs().get(n));
                            batchLoss += net.computeLoss(reader.getOutputs().get(n));
//                            loss+= net.computeLoss(reader.getOutputs().get(n));
                            batchEntryCount += 1;
                            net.backprop();
                            int index = reader.getRandomNumber(0,reader.getInputs().size());

                            if((n+1) % BATCH_SIZE == 0 || (n==reader.getInputs().size()-1)){

                                updateTrainingPictures(index,graphics);
                                LossGraph.getValues().add(batchLoss/batchEntryCount);
                                LossGraph.repaint();
                                EpochNumber.setText("Epoch: "+String.valueOf(i));
                                updateTraingProgress(i);

                                net.updateWeights();
                                batchEntryCount = 0;
                                batchLoss = 0;
                            }
                        }
//                        System.out.println(loss/reader.getInputs().size());
                        if(i % 10==9){
                            ValidationLabel.setText("Current Validation: "+MnistDataReader.validate(reader.getInputs(), reader.getOutputs(),net)*100+"%");
//                            System.out.println("validation = "+ MnistDataReader.validate(reader.getInputs(), reader.getOutputs(),net)*100+"%");
                        }
                    }
                    updateTraingProgress(EPOCH_ITERATIONS);
                    SaveNetworkActionPerformed(evt);
//                    System.out.println(-(time-System.nanoTime())/1000000);
            }
        }.start();

    }


    void updateOptiInLabel(String s){
        OptiInLabel.setText("This is the optimal input for "+s);
    }

    private void updateTraingProgress(int i) {
        TrainingProgress.setMinimum(0);
        TrainingProgress.setMaximum(EPOCH_ITERATIONS);
        TrainingProgress.setStringPainted(true);
        TrainingProgress.setValue((i));
    }

    private void SaveNetworkActionPerformed(java.awt.event.ActionEvent evt ) {
        if(net==null){
            errorOccured("No Network loaded");
            return;
        }
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            String src = fileToSave.getAbsolutePath();
            String path = src.replace("\\","/");
            System.out.println(path+".bin");
            net.saveNetwork(path+".bin");
        }
    }

    private void LoadDataActionPerformed(java.awt.event.ActionEvent evt){
        JFileChooser fileChooser = new JFileChooser();
        Graphics graphics = DataPicture.getGraphics();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Select a training data");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            JLabel dataLabel = new JLabel();
            dataLabel.setText("Loading: "+selectedFile.getAbsolutePath());

            JProgressBar progressBar = new JProgressBar();
            progressBar.setPreferredSize( new Dimension(470,25));

            JDialog downloadingDialog = new JDialog((JFrame)null ,"Loading Data...");
            javax.swing.GroupLayout downloadingDialogLayout = new javax.swing.GroupLayout(downloadingDialog);
            downloadingDialog.setLayout(downloadingDialogLayout);
            downloadingDialogLayout.setHorizontalGroup(
                    downloadingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downloadingDialogLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(downloadingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            downloadingDialogLayout.setVerticalGroup(
                    downloadingDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downloadingDialogLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                    .addComponent(dataLabel)
                                    .addContainerGap())
            );

            downloadingDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
            downloadingDialog.add(progressBar);
            downloadingDialog.setSize(500, 100);
            downloadingDialog.setLocation(600,400);
            downloadingDialog.setResizable(false);
            downloadingDialog.setVisible(true);

            new Thread(){
                public void run(){
                    try {
                        reader = new EMnistDataReader(selectedFile.getAbsolutePath().replaceAll("\'","/"),2,progressBar,dataLabel);
                        downloadingDialog.setVisible(false);
                        if(reader.getInputs().size()==0){
                            JOptionPane.showMessageDialog(new JFrame(), "Could not load images", "Error Occurred",
                                    JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(new JFrame(), reader.getInputs().size()+" images have been loaded", "Images Loaded",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        reader.shuffle();
                        updateTrainingPictures(reader.getRandomNumber(0,reader.getInputs().size()),graphics);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }.start();



//            downloadingDialog.setVisible(false);
        }


    }



    private void LoadNetworkActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("network file", "bin", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Select a network to load");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            String src = selectedFile.getAbsolutePath();
            String path = src.replace("\\","/");
            System.out.println(path);
            net = Network.loadNetwork(path);
            networkLoaded(selectedFile.getName());
            updateOverview();
            //            String source = "res/networks/test2.bin";

//            net = Network.loadNetwork("res/networks/net2.bin");


        }
    }


    void updateOutputs(double[] output) {
        for (int i = 0; i < output.length; i++) {
            progressBars[i].setMinimum(0);
            progressBars[i].setMaximum(1000);
            progressBars[i].setStringPainted(true);
            progressBars[i].setValue((int) (output[i] * 1000));
            progressBars[i].setString((int) (output[i] * 1000)/10d+"%");

        }

        this.revalidate();
        this.repaint();
    }

    void updateTrainingPictures(int i,Graphics graphics){

        if(DataPicture.isVisible() && DataPicture.isShowing() && reader.getInputs().size()!=0){
            graphics.drawImage(ImageLoader.tensorToImage(reader.getInputs().get(i),ImageLoader.NORMALISE), 0, 0, DataPicture.getWidth(), DataPicture.getWidth(), this);
            updateDataLabel(i);
        }

    }


    void updateDataLabel(int i){
//        System.out.println(reader.getOutputs().get(i));
        Grid out =  reader.getOutputs().get(i);
        DataLabel.setText("" + Classes.values()[out.indexOfMax()].getMeaning());
    }
    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {
        Graphics g = DrawPanel.getBufferedImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1000,1000);
        DrawPanel.repaint();
    }
    private void ComputeActionPerformed(java.awt.event.ActionEvent evt) {
        if(net!=null){
            BufferedImage img = DrawPanel.getBufferedImage();
            BufferedImage scaled = ImageLoader.getScaledImage(img, 128,128);

            DenseGrid o = new DenseGrid(128,128,1);

            for(int i = 0; i < 128; i++){
                for(int n = 0; n < 128; n++){
                    Color c = new Color(scaled.getRGB(i,n));
                    float v = c.getRed() / 255f;
                    o.set(i,n,0,v);
                }
            }
            DenseGrid out = (DenseGrid) net.compute(o);



//        ImageLoader.displayImage(ImageLoader.tensorToImage(network.getInputLayer().getOutput(), ImageLoader.NORMALISE));

            updateOutputs(out.raw());
            DataLossLabel.setText("" + Classes.values()[out.indexOfMax()].getMeaning());
        }else{
            errorOccured("No Network loaded");
        }

    }

    private javax.swing.JButton AddButton;
    private javax.swing.JButton ClearButton;
    private javax.swing.JPanel ComputedPanel;
    private javax.swing.JButton CreateButton;
    private javax.swing.JLabel DataLabel;
    private javax.swing.JButton DataLoadButton;
    private javax.swing.JPanel DataPicture;
    private javax.swing.JPanel DataWindow;
    private javax.swing.JPanel DrawGroupPanel;
    private DrawPanel DrawPanel;
    private javax.swing.JPanel DrawWindow;
    private javax.swing.JLabel EpochNumber;
    private javax.swing.JLabel Error;
    private javax.swing.JButton GoButton;
    private javax.swing.JPanel LayerImage;
    private javax.swing.JScrollBar LayerImageScroll;
    private javax.swing.JTextField LayerInfo;
    private javax.swing.JTextField LayerSettings;
    private javax.swing.JLabel LayerTitle;
    private LossGraph LossGraph;
    private javax.swing.JLabel LossLabel;
    private javax.swing.JTabbedPane MainUI;
    private javax.swing.JPanel NetworkCreate;
    private NetworkImageOverview NetworkImage;
    private javax.swing.JButton NetworkLoadButton;
    private javax.swing.JPanel OptiInImage;
    private javax.swing.JLabel OptiInLabel;
    private javax.swing.JButton OptimalInput;
    private javax.swing.JButton NetworkSaveButton;
    private javax.swing.JSplitPane OverviewPane;
    private javax.swing.JPanel OverviewWindow;
    private javax.swing.JPanel ProbabilitiesPanel;
    private javax.swing.JScrollPane ScrollNetOverview;
    private javax.swing.JButton StopButton;
    private javax.swing.JTextArea TextOverview;
    private javax.swing.JProgressBar TrainingProgress;
    private javax.swing.JButton TrainButton;
    private javax.swing.JPanel TrainingWindow;
    private javax.swing.JLabel ValidationLabel;
    private javax.swing.JLabel DataLossLabel;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton okErrorButton;


    public void updateOverview(){
        if(net == null){
            TextOverview.setText("No Network Loaded");
            NetworkImage.repaint();
        }else{
            TextOverview.setText(net.overview());
            NetworkImage.setNetwork(net);
            NetworkImage.repaint();
            LossGraph.repaint();
        }
    }

}
