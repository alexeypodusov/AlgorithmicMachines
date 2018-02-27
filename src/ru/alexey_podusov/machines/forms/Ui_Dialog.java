/********************************************************************************
 ** Form generated from reading ui file 'PreferencesDialog.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.8.7
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/
package ru.alexey_podusov.machines.forms;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Dialog implements com.trolltech.qt.QUiForm<QDialog>
{
    public QVBoxLayout verticalLayout_3;
    public QVBoxLayout verticalLayout;
    public QHBoxLayout horizontalLayout_3;
    public QSpacerItem horizontalSpacer_5;
    public QGroupBox groupBoxSpeed;
    public QVBoxLayout verticalLayout_2;
    public QRadioButton veryHightRadioButton;
    public QRadioButton hightRadioButton;
    public QRadioButton midleRadioButton;
    public QRadioButton lowRadioButton;
    public QRadioButton veryLowRadioButton;
    public QSpacerItem horizontalSpacer_6;
    public QHBoxLayout horizontalLayout_4;
    public QSpacerItem horizontalSpacer_7;
    public QCheckBox autoDeleteCheckbox;
    public QSpacerItem horizontalSpacer_8;
    public QHBoxLayout horizontalLayout;
    public QSpacerItem horizontalSpacer;
    public QLabel label;
    public QLineEdit finishRoleMarkovEdit;
    public QSpacerItem horizontalSpacer_2;
    public QHBoxLayout horizontalLayout_2;
    public QSpacerItem horizontalSpacer_3;
    public QPushButton cancelButton;
    public QPushButton okButton;
    public QSpacerItem horizontalSpacer_4;

    public Ui_Dialog() { super(); }

    public void setupUi(QDialog Dialog)
    {
        Dialog.setObjectName("Dialog");
        Dialog.resize(new QSize(285, 326).expandedTo(Dialog.minimumSizeHint()));
        Dialog.setMaximumSize(new QSize(16777215, 326));
        verticalLayout_3 = new QVBoxLayout(Dialog);
        verticalLayout_3.setObjectName("verticalLayout_3");
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        verticalLayout.setContentsMargins(-1, -1, -1, 0);
        horizontalLayout_3 = new QHBoxLayout();
        horizontalLayout_3.setObjectName("horizontalLayout_3");
        horizontalLayout_3.setContentsMargins(-1, -1, -1, 0);
        horizontalSpacer_5 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_3.addItem(horizontalSpacer_5);

        groupBoxSpeed = new QGroupBox(Dialog);
        groupBoxSpeed.setObjectName("groupBoxSpeed");
        verticalLayout_2 = new QVBoxLayout(groupBoxSpeed);
        verticalLayout_2.setObjectName("verticalLayout_2");
        veryHightRadioButton = new QRadioButton(groupBoxSpeed);
        veryHightRadioButton.setObjectName("veryHightRadioButton");

        verticalLayout_2.addWidget(veryHightRadioButton);

        hightRadioButton = new QRadioButton(groupBoxSpeed);
        hightRadioButton.setObjectName("hightRadioButton");

        verticalLayout_2.addWidget(hightRadioButton);

        midleRadioButton = new QRadioButton(groupBoxSpeed);
        midleRadioButton.setObjectName("midleRadioButton");

        verticalLayout_2.addWidget(midleRadioButton);

        lowRadioButton = new QRadioButton(groupBoxSpeed);
        lowRadioButton.setObjectName("lowRadioButton");

        verticalLayout_2.addWidget(lowRadioButton);

        veryLowRadioButton = new QRadioButton(groupBoxSpeed);
        veryLowRadioButton.setObjectName("veryLowRadioButton");

        verticalLayout_2.addWidget(veryLowRadioButton);


        horizontalLayout_3.addWidget(groupBoxSpeed);

        horizontalSpacer_6 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_3.addItem(horizontalSpacer_6);


        verticalLayout.addLayout(horizontalLayout_3);

        horizontalLayout_4 = new QHBoxLayout();
        horizontalLayout_4.setObjectName("horizontalLayout_4");
        horizontalSpacer_7 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_4.addItem(horizontalSpacer_7);

        autoDeleteCheckbox = new QCheckBox(Dialog);
        autoDeleteCheckbox.setObjectName("autoDeleteCheckbox");

        horizontalLayout_4.addWidget(autoDeleteCheckbox);

        horizontalSpacer_8 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_4.addItem(horizontalSpacer_8);


        verticalLayout.addLayout(horizontalLayout_4);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer);

        label = new QLabel(Dialog);
        label.setObjectName("label");

        horizontalLayout.addWidget(label);

        finishRoleMarkovEdit = new QLineEdit(Dialog);
        finishRoleMarkovEdit.setObjectName("finishRoleMarkovEdit");

        horizontalLayout.addWidget(finishRoleMarkovEdit);

        horizontalSpacer_2 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer_2);


        verticalLayout.addLayout(horizontalLayout);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        horizontalSpacer_3 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_2.addItem(horizontalSpacer_3);

        cancelButton = new QPushButton(Dialog);
        cancelButton.setObjectName("cancelButton");

        horizontalLayout_2.addWidget(cancelButton);

        okButton = new QPushButton(Dialog);
        okButton.setObjectName("okButton");

        horizontalLayout_2.addWidget(okButton);

        horizontalSpacer_4 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_2.addItem(horizontalSpacer_4);


        verticalLayout.addLayout(horizontalLayout_2);


        verticalLayout_3.addLayout(verticalLayout);

        retranslateUi(Dialog);

        Dialog.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog Dialog)
    {
        Dialog.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", null));
        groupBoxSpeed.setTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", null));
        veryHightRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0447\u0435\u043d\u044c \u0431\u044b\u0441\u0442\u0440\u0430\u044f", null));
        hightRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0411\u044b\u0441\u0442\u0440\u0430\u044f", null));
        midleRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u0440\u0435\u0434\u043d\u044f\u044f", null));
        lowRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041c\u0435\u0434\u043b\u0435\u043d\u043d\u0430\u044f", null));
        veryLowRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0447\u0435\u043d\u044c \u043c\u0435\u0434\u043b\u0435\u043d\u043d\u0430\u044f", null));
        autoDeleteCheckbox.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0410\u0432\u0442\u043e\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u0435 \u043f\u0443\u0441\u0442\u044b\u0445 \u0441\u0442\u0440\u043e\u043a", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u0438\u043c\u0432\u043e\u043b \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0438\u044f (\u041d.\u0410. \u041c\u0430\u0440\u043a\u043e\u0432\u0430)", null));
        cancelButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0442\u043c\u0435\u043d\u0430", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", null));
    } // retranslateUi

}

