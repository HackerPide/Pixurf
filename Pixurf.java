/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixurf;


/**
 *
 * @author hackerpide
 */
public class Pixurf extends javax.swing.JFrame {

    /**
     * Creates new form MainUI
     */
    public Pixurf() {
        initComponents();                         
        resetFields();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    double povH = 64.09;       // POV height
    double povCH = 46.07;      // POV crouched height
    double hBoxH = 72.04;      // Hitbox height
    double hBoxH2 = 72.03;     // Hitbox height used by 3rd+ players
    double hBoxCH = 54.04;     // Hitbox crouched height
    double hBoxCH2 = 54.03;    // Hitbox crouched height for 2nd player
    double wframeTemp;         // Wireframe input
    double lhTemp;             // Lowest height input
    double hhTemp;             // Highest height input
    double tempH;              // Free double variable
    String tempS;              // String to write to text areas
    int num1man;               // Number of pixurfs found
    int num2man;               // Number of pixurfs found
    int num3man;               // Number of pixurfs found
    int num4man;               // Number of pixurfs found
    int num5man;               // Number of pixurfs found
    int numTotal;              // Number of pixurfs found
    int i;                     // Free integer variable
    
    // Preset jump heights
    // First value is the height of the jump
    // Second value is for tickrate it works on (0=64 and 128, 1=only 128)
    // Third value is for rounding problems (0=no problem, 1=problem)
    
    // Normal jump height list
    double[][] normalJH = {
        {56.99, 0, 0},
        {56.94, 0, 0},
        {56.83, 1, 0},
        {56.68, 0, 1},
        {56.47, 1, 0},
        {56.22, 0, 0},
        {55.92, 1, 1},
        {55.57, 0, 1},
        {55.17, 1, 1},
        {54.72, 0, 0},
        {54.22, 1, 0},
        {53.68, 0, 1},
        {53.08, 1, 0},
        {52.44, 0, 0},
        {51.75, 1, 1},
        {51.00, 0, 0},
        {50.21, 1, 0},
        {49.37, 0, 0},
        {48.49, 1, 1},
        {47.55, 0, 0},
        {46.57, 1, 1},
        {45.53, 0, 0},
        {44.44, 1, 1},
        {43.32, 0, 1},
        {42.14, 1, 1},
        {40.91, 0, 1},
        {39.63, 1, 1},
        {38.30, 0, 0},
    };
    
    // Crouch jump height list
    double[][] crouchJH = {
        {65.99, 0, 0},
        {65.94, 0, 0},
        {65.83, 1, 0},
        {65.68, 0, 1},
        {65.47, 1, 0},
        {65.22, 0, 0},
        {64.92, 1, 1},
        {64.57, 0, 1},
        {64.17, 1, 1},
        {63.72, 0, 0},
        {63.22, 1, 0},
        {62.68, 0, 1},
        {62.08, 1, 0},
        {61.44, 0, 0},
        {60.75, 1, 1},
        {60.00, 0, 0},
        {59.21, 1, 0},
        {58.37, 0, 0},
        {57.49, 1, 1},
        {56.55, 0, 0},
        {55.57, 1, 1},
        {54.53, 0, 0},
        {53.44, 1, 1},
        {52.32, 0, 1},
        {51.14, 1, 1},
        {49.91, 0, 1},
        {48.63, 1, 1},
        {47.30, 0, 0},
    };
    double npLength = normalJH.length;
    double cpLength = crouchJH.length;
    
    
    public static void main(String[] args) {
        new Pixurf().setVisible(true);
    }
   
    
    void resetFields(){
        wfField.setText("");
        lhField.setText("");
        hhField.setText("");
        //Not doing these checks for now to keep it simple
        //These were meant for checking if inputs are double
        //whValiditiyLabel.setText("");
        //lwValidityLabel.setText("");
        //hhValidityLabel.setText("");
    }
    
    void resetTextAreas(){
        resultLabel.setText(" ");
        numLabel.setText(" ");
        oneNormalTxt.setText("");
        oneCrouchTxt.setText("");
        twoNormalTxt.setText("");
        twoCrouchTxt.setText("");
        twoWalkTxt.setText("");
        twoWalkCrouchTxt.setText("");
        threeTxt.setText("");
        threeCrouchTxt.setText("");
        three2CrouchTxt.setText("");
        fourTxt.setText("");
        fourCrouchTxt.setText("");
        four2CrouchTxt.setText("");
        fiveTxt.setText("");
        fiveCrouchTxt.setText("");
        five2CrouchTxt.setText("");
        numTotal = 0;
        num1man = 0;
        num2man = 0;
        num3man = 0;
        num4man = 0;
        num5man = 0;        
    }
    
    double singlePersonJump(double wframe, double preset){
        return wframe + povH - preset;
    }
    
    double twoManJump(double wframe, double preset){
        return wframe + povH - preset - hBoxH2;
    }
    
    double twoManWalkOff(double wframe){
        return wframe + povH - hBoxH;
    }
    
    double twoManWalkOffCrouch(double wframe){
        return wframe + povCH - hBoxCH2;
    }
    
    double threeManBoost(double wframe){
        return wframe + povH - hBoxH2 - hBoxH;
    }
    
    double threeManBoost1Crouch(double wframe){
        return wframe + povCH - hBoxH2 - hBoxCH;
    }
    
    double threeManBoost2Crouch(double wframe){
        return wframe + povCH - hBoxCH2 - hBoxCH;
    }
    
    double fourManBoost(double wframe){
        return wframe + povH - 2*hBoxH2 - hBoxH;
    }
    
    double fourManBoost1Crouch(double wframe){
        return wframe + povCH - 2*hBoxCH2 - hBoxCH;
    }
    
    double fourManBoost2Crouch(double wframe){
        return wframe + povCH - hBoxH2 - hBoxCH2 - hBoxCH;
    }
    
    double fiveManBoost(double wframe){
        return wframe + povH - 3*hBoxH2 - hBoxH;
    }
    
    double fiveManBoost1Crouch(double wframe){
        return wframe + povCH - 3*hBoxH2 - hBoxCH;
    }
    
    double fiveManBoost2Crouch(double wframe){
        return wframe + povCH - 2*hBoxH2 - hBoxCH2 - hBoxCH;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wfField = new javax.swing.JTextField();
        lhField = new javax.swing.JTextField();
        hhField = new javax.swing.JTextField();
        calcButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        oneNormalTxt = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        oneCrouchTxt = new javax.swing.JTextArea();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        twoNormalTxt = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        twoCrouchTxt = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        twoWalkTxt = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        twoWalkCrouchTxt = new javax.swing.JTextArea();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        threeTxt = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        threeCrouchTxt = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        three2CrouchTxt = new javax.swing.JTextArea();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        fourTxt = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        fourCrouchTxt = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        four2CrouchTxt = new javax.swing.JTextArea();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        fiveTxt = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        fiveCrouchTxt = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        five2CrouchTxt = new javax.swing.JTextArea();
        inputLabel = new javax.swing.JLabel();
        inputLabel1 = new javax.swing.JLabel();
        inputLabel2 = new javax.swing.JLabel();
        resultLabel = new javax.swing.JLabel();
        numLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pixel Surf Calculator   ");

        wfField.setFocusCycleRoot(true);
        wfField.setNextFocusableComponent(lhField);
        wfField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wfFieldActionPerformed(evt);
            }
        });

        lhField.setNextFocusableComponent(hhField);
        lhField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lhFieldActionPerformed(evt);
            }
        });

        hhField.setNextFocusableComponent(calcButton);
        hhField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hhFieldActionPerformed(evt);
            }
        });

        calcButton.setText("Calculate");
        calcButton.setNextFocusableComponent(resetButton);
        calcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        oneNormalTxt.setEditable(false);
        oneNormalTxt.setColumns(20);
        oneNormalTxt.setRows(5);
        jScrollPane1.setViewportView(oneNormalTxt);

        jTabbedPane2.addTab("Normal Jump", jScrollPane1);

        oneCrouchTxt.setEditable(false);
        oneCrouchTxt.setColumns(20);
        oneCrouchTxt.setRows(5);
        jScrollPane2.setViewportView(oneCrouchTxt);

        jTabbedPane2.addTab("Crouch Jump", jScrollPane2);

        jTabbedPane1.addTab("Solo", jTabbedPane2);

        twoNormalTxt.setEditable(false);
        twoNormalTxt.setColumns(20);
        twoNormalTxt.setRows(5);
        jScrollPane3.setViewportView(twoNormalTxt);

        jTabbedPane3.addTab("Normal Jump", jScrollPane3);

        twoCrouchTxt.setEditable(false);
        twoCrouchTxt.setColumns(20);
        twoCrouchTxt.setRows(5);
        jScrollPane4.setViewportView(twoCrouchTxt);

        jTabbedPane3.addTab("Crouch Jump", jScrollPane4);

        twoWalkTxt.setEditable(false);
        twoWalkTxt.setColumns(20);
        twoWalkTxt.setRows(5);
        jScrollPane5.setViewportView(twoWalkTxt);

        jTabbedPane3.addTab("Walk Off", jScrollPane5);

        twoWalkCrouchTxt.setEditable(false);
        twoWalkCrouchTxt.setColumns(20);
        twoWalkCrouchTxt.setRows(5);
        jScrollPane6.setViewportView(twoWalkCrouchTxt);

        jTabbedPane3.addTab("Walk Off Crouch", jScrollPane6);

        jTabbedPane1.addTab("2 Man", jTabbedPane3);

        threeTxt.setEditable(false);
        threeTxt.setColumns(20);
        threeTxt.setRows(5);
        jScrollPane7.setViewportView(threeTxt);

        jTabbedPane4.addTab("Boost", jScrollPane7);

        threeCrouchTxt.setEditable(false);
        threeCrouchTxt.setColumns(20);
        threeCrouchTxt.setRows(5);
        jScrollPane8.setViewportView(threeCrouchTxt);

        jTabbedPane4.addTab("Boost 1 Crouch", jScrollPane8);

        three2CrouchTxt.setEditable(false);
        three2CrouchTxt.setColumns(20);
        three2CrouchTxt.setRows(5);
        jScrollPane9.setViewportView(three2CrouchTxt);

        jTabbedPane4.addTab("Boost 2 Crouch", jScrollPane9);

        jTabbedPane1.addTab("3 Man", jTabbedPane4);

        fourTxt.setEditable(false);
        fourTxt.setColumns(20);
        fourTxt.setRows(5);
        jScrollPane10.setViewportView(fourTxt);

        jTabbedPane5.addTab("Boost", jScrollPane10);

        fourCrouchTxt.setEditable(false);
        fourCrouchTxt.setColumns(20);
        fourCrouchTxt.setRows(5);
        jScrollPane11.setViewportView(fourCrouchTxt);

        jTabbedPane5.addTab("Boost 1 Crouch", jScrollPane11);

        four2CrouchTxt.setEditable(false);
        four2CrouchTxt.setColumns(20);
        four2CrouchTxt.setRows(5);
        jScrollPane12.setViewportView(four2CrouchTxt);

        jTabbedPane5.addTab("Boost 2 Crouch", jScrollPane12);

        jTabbedPane1.addTab("4 Man", jTabbedPane5);

        fiveTxt.setEditable(false);
        fiveTxt.setColumns(20);
        fiveTxt.setRows(5);
        jScrollPane13.setViewportView(fiveTxt);

        jTabbedPane6.addTab("Boost", jScrollPane13);

        fiveCrouchTxt.setEditable(false);
        fiveCrouchTxt.setColumns(20);
        fiveCrouchTxt.setRows(5);
        jScrollPane14.setViewportView(fiveCrouchTxt);

        jTabbedPane6.addTab("Boost 1 Crouch", jScrollPane14);

        five2CrouchTxt.setEditable(false);
        five2CrouchTxt.setColumns(20);
        five2CrouchTxt.setRows(5);
        jScrollPane15.setViewportView(five2CrouchTxt);

        jTabbedPane6.addTab("Boost 2 Crouch", jScrollPane15);

        jTabbedPane1.addTab("5 Man", jTabbedPane6);

        inputLabel.setText("Enter wireframe height: ");

        inputLabel1.setText("Enter lowest height: ");

        inputLabel2.setText("Enter highest height: ");

        resultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultLabel.setText(" ");

        numLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numLabel.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputLabel2)
                        .addGap(56, 367, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTabbedPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(inputLabel)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(inputLabel1)
                                        .addGap(39, 39, 39)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(hhField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(lhField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wfField, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 59, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(calcButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(39, 39, 39))
                            .addComponent(resultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLabel)
                    .addComponent(wfField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLabel1)
                    .addComponent(lhField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLabel2)
                    .addComponent(hhField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numLabel)
                .addGap(8, 8, 8)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        resetFields();
        resetTextAreas();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void calcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonActionPerformed
        if (!wfField.getText().equals("")
                && !lhField.getText().equals("")
                && !hhField.getText().equals("")){
            resetTextAreas();
            wframeTemp = Double.parseDouble(wfField.getText());
            lhTemp = Double.parseDouble(lhField.getText());
            hhTemp = Double.parseDouble(hhField.getText());
            
            tempS = "";
            for (i = 0; i < npLength; i++){
                tempH = singlePersonJump(wframeTemp, normalJH[i][0]);
                if (lhTemp <= tempH && tempH <= hhTemp){
                    tempS += String.format("%.2f", tempH);
                    if (normalJH[i][1] == 1)
                        tempS += " - 128tick only";
                    if (normalJH[i][2] == 1)
                        tempS += " - Rounding problem";
                    tempS += "\n";
                    num1man++;
                }
            }
            oneNormalTxt.setText(tempS);
            
            tempS = "";
            for (i = 0; i < cpLength; i++){
                tempH = singlePersonJump(wframeTemp, crouchJH[i][0]);
                if (lhTemp <= tempH && tempH <= hhTemp){
                    tempS += String.format("%.2f", tempH);
                    if (crouchJH[i][1] == 1)
                        tempS += " - 128tick only";
                    if (crouchJH[i][2] == 1)
                        tempS += " - Rounding problem";
                    tempS += "\n";
                    num1man++;
                }
            }
            oneCrouchTxt.setText(tempS);
            
            tempS = "";
            for (i = 0; i < npLength; i++){
                tempH = twoManJump(wframeTemp, normalJH[i][0]);
                if (lhTemp <= tempH && tempH <= hhTemp){
                    tempS += String.format("%.2f", tempH);
                    if (normalJH[i][1] == 1)
                        tempS += " - 128tick only";
                    if (normalJH[i][2] == 1)
                        tempS += " - Rounding problem";
                    tempS += "\n";
                    num2man++;
                }
            }
            twoNormalTxt.setText(tempS);
            
            tempS = "";
            for (i = 0; i < cpLength; i++){
                tempH = twoManJump(wframeTemp, crouchJH[i][0]);
                if (lhTemp <= tempH && tempH <= hhTemp){
                    tempS += String.format("%.2f", tempH);
                    if (crouchJH[i][1] == 1)
                        tempS += " - 128tick only";
                    if (crouchJH[i][2] == 1)
                        tempS += " - Rounding problem";
                    tempS += "\n";
                    num2man++;
                }
            }
            twoCrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = twoManWalkOff(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num2man++;
            }
            twoWalkTxt.setText(tempS);
            
            tempS = "";
            tempH = twoManWalkOffCrouch(wframeTemp);
            if (lhTemp - povH + povCH <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num2man++;
            }
            twoWalkCrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = threeManBoost(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num3man++;
            }
            threeTxt.setText(tempS);
            
            tempS = "";
            tempH = threeManBoost1Crouch(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num3man++;
            }
            threeCrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = threeManBoost2Crouch(wframeTemp);
            if (lhTemp - povH + povCH <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num3man++;
            }
            three2CrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = fourManBoost(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num4man++;
            }
            fourTxt.setText(tempS);
            
            tempS = "";
            tempH = fourManBoost1Crouch(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num4man++;
            }
            fourCrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = fourManBoost2Crouch(wframeTemp);
            if (lhTemp - povH + povCH <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num4man++;
            }
            four2CrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = fiveManBoost(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num5man++;
            }
            fiveTxt.setText(tempS);
            
            tempS = "";
            tempH = fiveManBoost1Crouch(wframeTemp);
            if (lhTemp <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num5man++;
            }
            fiveCrouchTxt.setText(tempS);
            
            tempS = "";
            tempH = fiveManBoost2Crouch(wframeTemp);
            if (lhTemp - povH + povCH <= tempH && tempH <= hhTemp){
                tempS += String.format("%.2f", tempH) + "\n";
                num5man++;
            }
            five2CrouchTxt.setText(tempS);
            
            numTotal = num1man + num2man + num3man + num4man + num5man;
            
            resultLabel.setText("Found " + numTotal + " pixurfs");
            numLabel.setText("Solo: " + num1man + " ||"
                                + " 2 Man: " + num2man + " ||"
                                + " 3 Man: " + num3man + " ||"
                                + " 4 Man: " + num4man + " ||"
                                + " 5 Man: " + num5man);
        }
        else{
            resultLabel.setText("Please enter all values");
        }
    }//GEN-LAST:event_calcButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcButton;
    private javax.swing.JTextArea five2CrouchTxt;
    private javax.swing.JTextArea fiveCrouchTxt;
    private javax.swing.JTextArea fiveTxt;
    private javax.swing.JTextArea four2CrouchTxt;
    private javax.swing.JTextArea fourCrouchTxt;
    private javax.swing.JTextArea fourTxt;
    private javax.swing.JTextField hhField;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JLabel inputLabel1;
    private javax.swing.JLabel inputLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTextField lhField;
    private javax.swing.JLabel numLabel;
    private javax.swing.JTextArea oneCrouchTxt;
    private javax.swing.JTextArea oneNormalTxt;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JTextArea three2CrouchTxt;
    private javax.swing.JTextArea threeCrouchTxt;
    private javax.swing.JTextArea threeTxt;
    private javax.swing.JTextArea twoCrouchTxt;
    private javax.swing.JTextArea twoNormalTxt;
    private javax.swing.JTextArea twoWalkCrouchTxt;
    private javax.swing.JTextArea twoWalkTxt;
    private javax.swing.JTextField wfField;
    // End of variables declaration//GEN-END:variables
}
