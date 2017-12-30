/********************************************************************************
 ** Form generated from reading ui file 'PostWorkAreaWidget.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.8.7
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/
package ru.alexey_podusov.machines.forms.post;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_PostWorkAreaWidget implements com.trolltech.qt.QUiForm<QWidget>
{
    public QHBoxLayout horizontalLayout_2;
    public QHBoxLayout workAreaLayout;
    public QPushButton LeftPushButton;
    public QScrollArea scrollArea;
    public QWidget scrollAreaWidgetContents;
    public QPushButton RightPushButton;

    public Ui_PostWorkAreaWidget() { super(); }

    public void setupUi(QWidget PostWorkAreaWidget)
    {
        PostWorkAreaWidget.setObjectName("PostWorkAreaWidget");
        PostWorkAreaWidget.resize(new QSize(578, 106).expandedTo(PostWorkAreaWidget.minimumSizeHint()));
        PostWorkAreaWidget.setMinimumSize(new QSize(150, 0));
        PostWorkAreaWidget.setToolTip("");
        horizontalLayout_2 = new QHBoxLayout(PostWorkAreaWidget);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        workAreaLayout = new QHBoxLayout();
        workAreaLayout.setObjectName("workAreaLayout");
        LeftPushButton = new QPushButton(PostWorkAreaWidget);
        LeftPushButton.setObjectName("LeftPushButton");
        LeftPushButton.setMinimumSize(new QSize(30, 70));
        LeftPushButton.setMaximumSize(new QSize(30, 70));

        workAreaLayout.addWidget(LeftPushButton);

        scrollArea = new QScrollArea(PostWorkAreaWidget);
        scrollArea.setObjectName("scrollArea");
        scrollArea.setVerticalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        scrollArea.setHorizontalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        scrollArea.setWidgetResizable(true);
        scrollAreaWidgetContents = new QWidget();
        scrollAreaWidgetContents.setObjectName("scrollAreaWidgetContents");
        scrollAreaWidgetContents.setGeometry(new QRect(0, 0, 484, 84));
        scrollArea.setWidget(scrollAreaWidgetContents);

        workAreaLayout.addWidget(scrollArea);

        RightPushButton = new QPushButton(PostWorkAreaWidget);
        RightPushButton.setObjectName("RightPushButton");
        RightPushButton.setMinimumSize(new QSize(30, 70));
        RightPushButton.setMaximumSize(new QSize(30, 70));

        workAreaLayout.addWidget(RightPushButton);


        horizontalLayout_2.addLayout(workAreaLayout);

        retranslateUi(PostWorkAreaWidget);

        PostWorkAreaWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget PostWorkAreaWidget)
    {
        PostWorkAreaWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "Form", null));
        LeftPushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "PushButton", null));
        RightPushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "PushButton", null));
    } // retranslateUi

}

