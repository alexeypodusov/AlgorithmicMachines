/********************************************************************************
 ** Form generated from reading ui file 'MainWindow.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.8.7
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/
package ru.alexey_podusov.machines.forms;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_MainWindow implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QAction actionPlay;
    public QAction actionNextStep;
    public QAction actionReverseStep;
    public QAction actionPause;
    public QAction actionStop;
    public QWidget centralWidget;
    public QVBoxLayout verticalLayout_2;
    public QVBoxLayout mainVerticalLayout;
    public QVBoxLayout taskLayout;
    public QLabel labelTask;
    public QTextEdit taskTextEdit;
    public QVBoxLayout workAreaLayout;
    public QTabWidget tabWorkAreaWidget;
    public QHBoxLayout commandBarLayout;
    public QVBoxLayout buttonsVerticalLayout;
    public QSpacerItem verticalSpacer;
    public QPushButton pushButtonAddString;
    public QPushButton pushButtonDeleteString;
    public QSpacerItem verticalSpacer_2;
    public QVBoxLayout commandAndButtonslLayout;
    public QHBoxLayout buttonHorizontalLayout;
    public QPushButton backCommandButton;
    public QPushButton forwardCommandButton;
    public QSpacerItem horizontalSpacer;
    public QVBoxLayout commandLayout;
    public QTabWidget tabCommandWidget;
    public QMenuBar menuBar;
    public QMenu menu;
    public QMenu menu_2;
    public QMenu menu_3;
    public QStatusBar statusBar;
    public QToolBar mainToolBar;

    public Ui_MainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow)
    {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(640, 510).expandedTo(MainWindow.minimumSizeHint()));
        actionPlay = new QAction(MainWindow);
        actionPlay.setObjectName("actionPlay");
        actionNextStep = new QAction(MainWindow);
        actionNextStep.setObjectName("actionNextStep");
        actionReverseStep = new QAction(MainWindow);
        actionReverseStep.setObjectName("actionReverseStep");
        actionPause = new QAction(MainWindow);
        actionPause.setObjectName("actionPause");
        actionStop = new QAction(MainWindow);
        actionStop.setObjectName("actionStop");
        centralWidget = new QWidget(MainWindow);
        centralWidget.setObjectName("centralWidget");
        verticalLayout_2 = new QVBoxLayout(centralWidget);
        verticalLayout_2.setSpacing(6);
        verticalLayout_2.setMargin(11);
        verticalLayout_2.setObjectName("verticalLayout_2");
        mainVerticalLayout = new QVBoxLayout();
        mainVerticalLayout.setSpacing(6);
        mainVerticalLayout.setMargin(11);
        mainVerticalLayout.setObjectName("mainVerticalLayout");
        taskLayout = new QVBoxLayout();
        taskLayout.setSpacing(6);
        taskLayout.setMargin(11);
        taskLayout.setObjectName("taskLayout");
        labelTask = new QLabel(centralWidget);
        labelTask.setObjectName("labelTask");
        labelTask.setMaximumSize(new QSize(16777215, 20));

        taskLayout.addWidget(labelTask);

        taskTextEdit = new QTextEdit(centralWidget);
        taskTextEdit.setObjectName("taskTextEdit");
        taskTextEdit.setMinimumSize(new QSize(0, 40));
        taskTextEdit.setMaximumSize(new QSize(16777215, 80));

        taskLayout.addWidget(taskTextEdit);


        mainVerticalLayout.addLayout(taskLayout);

        workAreaLayout = new QVBoxLayout();
        workAreaLayout.setSpacing(6);
        workAreaLayout.setMargin(11);
        workAreaLayout.setObjectName("workAreaLayout");
        tabWorkAreaWidget = new QTabWidget(centralWidget);
        tabWorkAreaWidget.setObjectName("tabWorkAreaWidget");
        tabWorkAreaWidget.setMaximumSize(new QSize(16777215, 140));

        workAreaLayout.addWidget(tabWorkAreaWidget);


        mainVerticalLayout.addLayout(workAreaLayout);

        commandBarLayout = new QHBoxLayout();
        commandBarLayout.setSpacing(6);
        commandBarLayout.setMargin(11);
        commandBarLayout.setObjectName("commandBarLayout");
        buttonsVerticalLayout = new QVBoxLayout();
        buttonsVerticalLayout.setSpacing(6);
        buttonsVerticalLayout.setMargin(11);
        buttonsVerticalLayout.setObjectName("buttonsVerticalLayout");
        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        buttonsVerticalLayout.addItem(verticalSpacer);

        pushButtonAddString = new QPushButton(centralWidget);
        pushButtonAddString.setObjectName("pushButtonAddString");
        pushButtonAddString.setMaximumSize(new QSize(30, 16777215));

        buttonsVerticalLayout.addWidget(pushButtonAddString);

        pushButtonDeleteString = new QPushButton(centralWidget);
        pushButtonDeleteString.setObjectName("pushButtonDeleteString");
        pushButtonDeleteString.setMaximumSize(new QSize(30, 16777215));

        buttonsVerticalLayout.addWidget(pushButtonDeleteString);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        buttonsVerticalLayout.addItem(verticalSpacer_2);


        commandBarLayout.addLayout(buttonsVerticalLayout);

        commandAndButtonslLayout = new QVBoxLayout();
        commandAndButtonslLayout.setSpacing(3);
        commandAndButtonslLayout.setMargin(11);
        commandAndButtonslLayout.setObjectName("commandAndButtonslLayout");
        commandAndButtonslLayout.setContentsMargins(0, -1, -1, -1);
        buttonHorizontalLayout = new QHBoxLayout();
        buttonHorizontalLayout.setSpacing(6);
        buttonHorizontalLayout.setMargin(11);
        buttonHorizontalLayout.setObjectName("buttonHorizontalLayout");
        buttonHorizontalLayout.setContentsMargins(-1, 0, -1, -1);
        backCommandButton = new QPushButton(centralWidget);
        backCommandButton.setObjectName("backCommandButton");

        buttonHorizontalLayout.addWidget(backCommandButton);

        forwardCommandButton = new QPushButton(centralWidget);
        forwardCommandButton.setObjectName("forwardCommandButton");

        buttonHorizontalLayout.addWidget(forwardCommandButton);

        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        buttonHorizontalLayout.addItem(horizontalSpacer);


        commandAndButtonslLayout.addLayout(buttonHorizontalLayout);

        commandLayout = new QVBoxLayout();
        commandLayout.setSpacing(6);
        commandLayout.setMargin(11);
        commandLayout.setObjectName("commandLayout");
        tabCommandWidget = new QTabWidget(centralWidget);
        tabCommandWidget.setObjectName("tabCommandWidget");

        commandLayout.addWidget(tabCommandWidget);


        commandAndButtonslLayout.addLayout(commandLayout);


        commandBarLayout.addLayout(commandAndButtonslLayout);


        mainVerticalLayout.addLayout(commandBarLayout);


        verticalLayout_2.addLayout(mainVerticalLayout);

        MainWindow.setCentralWidget(centralWidget);
        menuBar = new QMenuBar(MainWindow);
        menuBar.setObjectName("menuBar");
        menuBar.setGeometry(new QRect(0, 0, 640, 21));
        menu = new QMenu(menuBar);
        menu.setObjectName("menu");
        menu_2 = new QMenu(menuBar);
        menu_2.setObjectName("menu_2");
        menu_3 = new QMenu(menuBar);
        menu_3.setObjectName("menu_3");
        MainWindow.setMenuBar(menuBar);
        statusBar = new QStatusBar(MainWindow);
        statusBar.setObjectName("statusBar");
        MainWindow.setStatusBar(statusBar);
        mainToolBar = new QToolBar(MainWindow);
        mainToolBar.setObjectName("mainToolBar");
        MainWindow.addToolBar(com.trolltech.qt.core.Qt.ToolBarArea.TopToolBarArea, mainToolBar);

        menuBar.addAction(menu.menuAction());
        menuBar.addAction(menu_2.menuAction());
        menuBar.addAction(menu_3.menuAction());
        menu_3.addAction(actionPlay);
        menu_3.addAction(actionNextStep);
        menu_3.addAction(actionReverseStep);
        menu_3.addAction(actionPause);
        menu_3.addAction(actionStop);
        retranslateUi(MainWindow);

        tabWorkAreaWidget.setCurrentIndex(-1);
        tabCommandWidget.setCurrentIndex(-1);


        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "MainWindow", null));
        actionPlay.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0417\u0430\u043f\u0443\u0441\u043a", null));
        actionNextStep.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0421\u043b\u0435\u0434\u0443\u044e\u0449\u0438\u0439 \u0448\u0430\u0433", null));
        actionReverseStep.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041f\u0440\u0435\u0434\u044b\u0434\u0443\u0449\u0438\u0439 \u0448\u0430\u0433", null));
        actionPause.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041f\u0430\u0443\u0437\u0430", null));
        actionStop.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0421\u0442\u043e\u043f", null));
        labelTask.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0423\u0441\u043b\u043e\u0432\u0438\u0435", null));
        pushButtonAddString.setText("");
        pushButtonDeleteString.setText("");
        backCommandButton.setText("");
        forwardCommandButton.setText("");
        menu.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0424\u0430\u0439\u043b", null));
        menu_2.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041c\u0430\u0448\u0438\u043d\u0430", null));
        menu_3.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0412\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u0435", null));
    } // retranslateUi

}

