/********************************************************************************
 ** Form generated from reading ui file 'PostCommandWidget.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.8.7
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/
package ru.alexey_podusov.machines.forms.post;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_PostCommandWidget implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout_2;
    public QVBoxLayout verticalLayout;
    public QHBoxLayout horizontalLayout;
    public QLabel label_2;
    public QLabel label;
    public QLabel label_3;
    public QLabel label_4;
    public QScrollArea scrollArea;
    public QWidget scrollAreaWidgetContents_2;

    public Ui_PostCommandWidget() { super(); }

    public void setupUi(QWidget PostCommandWidget)
    {
        PostCommandWidget.setObjectName("PostCommandWidget");
        PostCommandWidget.resize(new QSize(400, 300).expandedTo(PostCommandWidget.minimumSizeHint()));
        verticalLayout_2 = new QVBoxLayout(PostCommandWidget);
        verticalLayout_2.setObjectName("verticalLayout_2");
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        label_2 = new QLabel(PostCommandWidget);
        label_2.setObjectName("label_2");

        horizontalLayout.addWidget(label_2);

        label = new QLabel(PostCommandWidget);
        label.setObjectName("label");

        horizontalLayout.addWidget(label);

        label_3 = new QLabel(PostCommandWidget);
        label_3.setObjectName("label_3");

        horizontalLayout.addWidget(label_3);

        label_4 = new QLabel(PostCommandWidget);
        label_4.setObjectName("label_4");

        horizontalLayout.addWidget(label_4);


        verticalLayout.addLayout(horizontalLayout);

        scrollArea = new QScrollArea(PostCommandWidget);
        scrollArea.setObjectName("scrollArea");
        scrollArea.setWidgetResizable(true);
        scrollAreaWidgetContents_2 = new QWidget();
        scrollAreaWidgetContents_2.setObjectName("scrollAreaWidgetContents_2");
        scrollAreaWidgetContents_2.setGeometry(new QRect(0, 0, 378, 257));
        scrollArea.setWidget(scrollAreaWidgetContents_2);

        verticalLayout.addWidget(scrollArea);


        verticalLayout_2.addLayout(verticalLayout);

        retranslateUi(PostCommandWidget);

        PostCommandWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget PostCommandWidget)
    {
        PostCommandWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("PostCommandWidget", "Form", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("PostCommandWidget", "\u2116", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("PostCommandWidget", "\u041a\u043e\u043c\u0430\u043d\u0434\u0430", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("PostCommandWidget", "\u041f\u0435\u0440\u0435\u0445\u043e\u0434", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("PostCommandWidget", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439", null));
    } // retranslateUi

}

