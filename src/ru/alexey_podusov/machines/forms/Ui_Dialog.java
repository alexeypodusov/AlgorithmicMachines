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
    public QLineEdit finishRoleMarkovEdit;
    public QCheckBox autoDeleteCheckbox;
    public QPushButton okButton;
    public QLabel label;
    public QGroupBox groupBoxSpeed;
    public QRadioButton hightRadioButton;
    public QRadioButton midleRadioButton;
    public QRadioButton lowRadioButton;
    public QRadioButton veryHightRadioButton;
    public QRadioButton veryLowRadioButton;
    public QPushButton cancelButton;

    public Ui_Dialog() { super(); }

    public void setupUi(QDialog Dialog)
    {
        Dialog.setObjectName("Dialog");
        Dialog.resize(new QSize(333, 326).expandedTo(Dialog.minimumSizeHint()));
        finishRoleMarkovEdit = new QLineEdit(Dialog);
        finishRoleMarkovEdit.setObjectName("finishRoleMarkovEdit");
        finishRoleMarkovEdit.setGeometry(new QRect(220, 240, 91, 20));
        autoDeleteCheckbox = new QCheckBox(Dialog);
        autoDeleteCheckbox.setObjectName("autoDeleteCheckbox");
        autoDeleteCheckbox.setGeometry(new QRect(60, 210, 251, 17));
        okButton = new QPushButton(Dialog);
        okButton.setObjectName("okButton");
        okButton.setGeometry(new QRect(210, 280, 75, 23));
        label = new QLabel(Dialog);
        label.setObjectName("label");
        label.setGeometry(new QRect(20, 240, 191, 16));
        groupBoxSpeed = new QGroupBox(Dialog);
        groupBoxSpeed.setObjectName("groupBoxSpeed");
        groupBoxSpeed.setGeometry(new QRect(100, 20, 141, 171));
        hightRadioButton = new QRadioButton(groupBoxSpeed);
        hightRadioButton.setObjectName("hightRadioButton");
        hightRadioButton.setGeometry(new QRect(10, 50, 91, 17));
        midleRadioButton = new QRadioButton(groupBoxSpeed);
        midleRadioButton.setObjectName("midleRadioButton");
        midleRadioButton.setGeometry(new QRect(10, 80, 81, 17));
        lowRadioButton = new QRadioButton(groupBoxSpeed);
        lowRadioButton.setObjectName("lowRadioButton");
        lowRadioButton.setGeometry(new QRect(10, 110, 91, 17));
        veryHightRadioButton = new QRadioButton(groupBoxSpeed);
        veryHightRadioButton.setObjectName("veryHightRadioButton");
        veryHightRadioButton.setGeometry(new QRect(10, 20, 101, 17));
        veryLowRadioButton = new QRadioButton(groupBoxSpeed);
        veryLowRadioButton.setObjectName("veryLowRadioButton");
        veryLowRadioButton.setGeometry(new QRect(10, 140, 121, 17));
        cancelButton = new QPushButton(Dialog);
        cancelButton.setObjectName("cancelButton");
        cancelButton.setGeometry(new QRect(60, 280, 75, 23));
        retranslateUi(Dialog);

        Dialog.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog Dialog)
    {
        Dialog.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", null));
        autoDeleteCheckbox.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0410\u0432\u0442\u043e\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u0435 \u043f\u0443\u0441\u0442\u044b\u0445 \u0441\u0442\u0440\u043e\u043a", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u0438\u043c\u0432\u043e\u043b \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0438\u044f (\u041d.\u0410. \u041c\u0430\u0440\u043a\u043e\u0432\u0430)", null));
        groupBoxSpeed.setTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", null));
        hightRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0411\u044b\u0441\u0442\u0440\u0430\u044f", null));
        midleRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u0421\u0440\u0435\u0434\u043d\u044f\u044f", null));
        lowRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041c\u0435\u0434\u043b\u0435\u043d\u043d\u0430\u044f", null));
        veryHightRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0447\u0435\u043d\u044c \u0431\u044b\u0441\u0442\u0440\u0430\u044f", null));
        veryLowRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0447\u0435\u043d\u044c \u043c\u0435\u0434\u043b\u0435\u043d\u043d\u0430\u044f", null));
        cancelButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "\u041e\u0442\u043c\u0435\u043d\u0430", null));
    } // retranslateUi

}

