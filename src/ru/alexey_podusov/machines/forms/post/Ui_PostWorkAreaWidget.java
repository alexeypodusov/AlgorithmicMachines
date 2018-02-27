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
    public QVBoxLayout mainLayout;
    public QHBoxLayout cellsLayout;
    public QPushButton LeftPushButton;
    public QScrollArea scrollArea;
    public QWidget scrollAreaWidgetContents;
    public QPushButton RightPushButton;
    public QHBoxLayout bottomButtonLayout;
    public QPushButton restoreButton;
    public QSpacerItem horizontalSpacer_2;
    public QLabel label;
    public QLineEdit goToLineEdit;

    public Ui_PostWorkAreaWidget() { super(); }

    public void setupUi(QWidget PostWorkAreaWidget)
    {
        PostWorkAreaWidget.setObjectName("PostWorkAreaWidget");
        PostWorkAreaWidget.resize(new QSize(699, 267).expandedTo(PostWorkAreaWidget.minimumSizeHint()));
        PostWorkAreaWidget.setMinimumSize(new QSize(150, 0));
        PostWorkAreaWidget.setToolTip("");
        horizontalLayout_2 = new QHBoxLayout(PostWorkAreaWidget);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        mainLayout = new QVBoxLayout();
        mainLayout.setObjectName("mainLayout");
        mainLayout.setContentsMargins(0, -1, -1, 0);
        cellsLayout = new QHBoxLayout();
        cellsLayout.setObjectName("cellsLayout");
        LeftPushButton = new QPushButton(PostWorkAreaWidget);
        LeftPushButton.setObjectName("LeftPushButton");
        LeftPushButton.setMinimumSize(new QSize(30, 70));
        LeftPushButton.setMaximumSize(new QSize(30, 85));

        cellsLayout.addWidget(LeftPushButton);

        scrollArea = new QScrollArea(PostWorkAreaWidget);
        scrollArea.setObjectName("scrollArea");
        scrollArea.setVerticalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        scrollArea.setHorizontalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        scrollArea.setWidgetResizable(true);
        scrollAreaWidgetContents = new QWidget();
        scrollAreaWidgetContents.setObjectName("scrollAreaWidgetContents");
        scrollAreaWidgetContents.setGeometry(new QRect(0, 0, 603, 212));
        scrollArea.setWidget(scrollAreaWidgetContents);

        cellsLayout.addWidget(scrollArea);

        RightPushButton = new QPushButton(PostWorkAreaWidget);
        RightPushButton.setObjectName("RightPushButton");
        RightPushButton.setMinimumSize(new QSize(30, 70));
        RightPushButton.setMaximumSize(new QSize(30, 85));

        cellsLayout.addWidget(RightPushButton);


        mainLayout.addLayout(cellsLayout);

        bottomButtonLayout = new QHBoxLayout();
        bottomButtonLayout.setObjectName("bottomButtonLayout");
        bottomButtonLayout.setContentsMargins(-1, 0, -1, -1);
        restoreButton = new QPushButton(PostWorkAreaWidget);
        restoreButton.setObjectName("restoreButton");
        restoreButton.setMinimumSize(new QSize(180, 0));

        bottomButtonLayout.addWidget(restoreButton);

        horizontalSpacer_2 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        bottomButtonLayout.addItem(horizontalSpacer_2);

        label = new QLabel(PostWorkAreaWidget);
        label.setObjectName("label");

        bottomButtonLayout.addWidget(label);

        goToLineEdit = new QLineEdit(PostWorkAreaWidget);
        goToLineEdit.setObjectName("goToLineEdit");
        goToLineEdit.setMaximumSize(new QSize(50, 16777215));
        goToLineEdit.setMaxLength(4);

        bottomButtonLayout.addWidget(goToLineEdit);


        mainLayout.addLayout(bottomButtonLayout);


        horizontalLayout_2.addLayout(mainLayout);

        retranslateUi(PostWorkAreaWidget);

        PostWorkAreaWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget PostWorkAreaWidget)
    {
        PostWorkAreaWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "Form", null));
        LeftPushButton.setText("");
        RightPushButton.setText("");
        restoreButton.setText(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "\u0412\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u043b\u0435\u043d\u0442\u0443", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("PostWorkAreaWidget", "\u041f\u0435\u0440\u0435\u043a\u0442\u0438 \u043a: ", null));
    } // retranslateUi

}

