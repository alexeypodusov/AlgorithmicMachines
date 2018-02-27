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
    public QAction actionNew;
    public QAction actionOpen;
    public QAction actionSave;
    public QAction actionSaveAs;
    public QAction actionExit;
    public QAction actionPost;
    public QAction actionTyuring;
    public QAction actionMarkov;
    public QAction actionPreferences;
    public QWidget centralWidget;
    public QVBoxLayout verticalLayout_2;
    public QVBoxLayout mainVerticalLayout;
    public QVBoxLayout taskLayout;
    public QLabel labelTask;
    public QTextEdit taskTextEdit;
    public QVBoxLayout workAreaLayout;
    public QHBoxLayout playBarLayout;
    public QHBoxLayout commandBarLayout;
    public QVBoxLayout buttonsVerticalLayout;
    public QPushButton insertBeforeButton;
    public QPushButton insertAfterButton;
    public QPushButton deleteCommand;
    public QSpacerItem verticalSpacer_2;
    public QVBoxLayout commandAndButtonslLayout;
    public QSpacerItem horizontalSpacer;
    public QHBoxLayout buttonHorizontalLayout;
    public QPushButton backCommandButton;
    public QPushButton forwardCommandButton;
    public QVBoxLayout commandLayout;
    public QMenuBar menuBar;
    public QMenu menu;
    public QMenu menu_2;
    public QMenu menu_3;
    public QMenu menu_4;
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
        actionNew = new QAction(MainWindow);
        actionNew.setObjectName("actionNew");
        actionOpen = new QAction(MainWindow);
        actionOpen.setObjectName("actionOpen");
        actionSave = new QAction(MainWindow);
        actionSave.setObjectName("actionSave");
        actionSaveAs = new QAction(MainWindow);
        actionSaveAs.setObjectName("actionSaveAs");
        actionExit = new QAction(MainWindow);
        actionExit.setObjectName("actionExit");
        actionPost = new QAction(MainWindow);
        actionPost.setObjectName("actionPost");
        actionTyuring = new QAction(MainWindow);
        actionTyuring.setObjectName("actionTyuring");
        actionMarkov = new QAction(MainWindow);
        actionMarkov.setObjectName("actionMarkov");
        actionPreferences = new QAction(MainWindow);
        actionPreferences.setObjectName("actionPreferences");
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

        mainVerticalLayout.addLayout(workAreaLayout);

        playBarLayout = new QHBoxLayout();
        playBarLayout.setSpacing(6);
        playBarLayout.setMargin(11);
        playBarLayout.setObjectName("playBarLayout");

        mainVerticalLayout.addLayout(playBarLayout);

        commandBarLayout = new QHBoxLayout();
        commandBarLayout.setSpacing(6);
        commandBarLayout.setMargin(11);
        commandBarLayout.setObjectName("commandBarLayout");
        buttonsVerticalLayout = new QVBoxLayout();
        buttonsVerticalLayout.setSpacing(6);
        buttonsVerticalLayout.setMargin(11);
        buttonsVerticalLayout.setObjectName("buttonsVerticalLayout");
        buttonsVerticalLayout.setContentsMargins(-1, 30, -1, -1);
        insertBeforeButton = new QPushButton(centralWidget);
        insertBeforeButton.setObjectName("insertBeforeButton");
        insertBeforeButton.setMaximumSize(new QSize(30, 16777215));

        buttonsVerticalLayout.addWidget(insertBeforeButton);

        insertAfterButton = new QPushButton(centralWidget);
        insertAfterButton.setObjectName("insertAfterButton");
        insertAfterButton.setMaximumSize(new QSize(30, 16777215));

        buttonsVerticalLayout.addWidget(insertAfterButton);

        deleteCommand = new QPushButton(centralWidget);
        deleteCommand.setObjectName("deleteCommand");
        deleteCommand.setMaximumSize(new QSize(30, 16777215));

        buttonsVerticalLayout.addWidget(deleteCommand);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        buttonsVerticalLayout.addItem(verticalSpacer_2);


        commandBarLayout.addLayout(buttonsVerticalLayout);

        commandAndButtonslLayout = new QVBoxLayout();
        commandAndButtonslLayout.setSpacing(3);
        commandAndButtonslLayout.setMargin(11);
        commandAndButtonslLayout.setObjectName("commandAndButtonslLayout");
        commandAndButtonslLayout.setContentsMargins(0, -1, -1, -1);
        horizontalSpacer = new QSpacerItem(40, 0, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        commandAndButtonslLayout.addItem(horizontalSpacer);

        buttonHorizontalLayout = new QHBoxLayout();
        buttonHorizontalLayout.setSpacing(6);
        buttonHorizontalLayout.setMargin(11);
        buttonHorizontalLayout.setObjectName("buttonHorizontalLayout");
        buttonHorizontalLayout.setContentsMargins(-1, 0, -1, -1);
        backCommandButton = new QPushButton(centralWidget);
        backCommandButton.setObjectName("backCommandButton");
        backCommandButton.setMaximumSize(new QSize(30, 16777215));

        buttonHorizontalLayout.addWidget(backCommandButton);

        forwardCommandButton = new QPushButton(centralWidget);
        forwardCommandButton.setObjectName("forwardCommandButton");
        forwardCommandButton.setMaximumSize(new QSize(30, 16777215));

        buttonHorizontalLayout.addWidget(forwardCommandButton);


        commandAndButtonslLayout.addLayout(buttonHorizontalLayout);

        commandLayout = new QVBoxLayout();
        commandLayout.setSpacing(6);
        commandLayout.setMargin(11);
        commandLayout.setObjectName("commandLayout");

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
        menu_4 = new QMenu(menuBar);
        menu_4.setObjectName("menu_4");
        MainWindow.setMenuBar(menuBar);
        statusBar = new QStatusBar(MainWindow);
        statusBar.setObjectName("statusBar");
        MainWindow.setStatusBar(statusBar);
        mainToolBar = new QToolBar(MainWindow);
        mainToolBar.setObjectName("mainToolBar");
        mainToolBar.setToolButtonStyle(com.trolltech.qt.core.Qt.ToolButtonStyle.ToolButtonTextUnderIcon);
        MainWindow.addToolBar(com.trolltech.qt.core.Qt.ToolBarArea.TopToolBarArea, mainToolBar);

        menuBar.addAction(menu.menuAction());
        menuBar.addAction(menu_2.menuAction());
        menuBar.addAction(menu_3.menuAction());
        menuBar.addAction(menu_4.menuAction());
        menu.addAction(actionNew);
        menu.addAction(actionOpen);
        menu.addSeparator();
        menu.addAction(actionSave);
        menu.addAction(actionSaveAs);
        menu.addSeparator();
        menu.addAction(actionExit);
        menu_2.addAction(actionPost);
        menu_2.addAction(actionTyuring);
        menu_2.addAction(actionMarkov);
        menu_3.addAction(actionPlay);
        menu_3.addAction(actionNextStep);
        menu_3.addAction(actionReverseStep);
        menu_3.addAction(actionPause);
        menu_3.addAction(actionStop);
        menu_4.addAction(actionPreferences);
        retranslateUi(MainWindow);

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
        actionNew.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041d\u043e\u0432\u044b\u0439", null));
        actionOpen.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041e\u0442\u043a\u0440\u044b\u0442\u044c", null));
        actionSave.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", null));
        actionSaveAs.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u0430\u043a", null));
        actionExit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0412\u044b\u0445\u043e\u0434", null));
        actionPost.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041c\u0430\u0448\u0438\u043d\u0430 \u041f\u043e\u0441\u0442\u0430", null));
        actionTyuring.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041c\u0430\u0448\u0438\u043d\u0430 \u0422\u044c\u044e\u0440\u0438\u043d\u0433\u0430", null));
        actionMarkov.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041d\u043e\u0440\u043c\u0430\u043b\u044c\u043d\u044b\u0435 \u0430\u043b\u0433\u043e\u0440\u0438\u0442\u043c\u044b \u041c\u0430\u0440\u043a\u043e\u0432\u0430", null));
        actionPreferences.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", null));
        labelTask.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0423\u0441\u043b\u043e\u0432\u0438\u0435", null));
        insertBeforeButton.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "<html><head/><body><p>\u0412\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u043f\u0435\u0440\u0435\u0434</p></body></html>", null));
        insertBeforeButton.setText("");
        insertAfterButton.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "<html><head/><body><p>\u0412\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u043f\u043e\u0441\u043b\u0435</p></body></html>", null));
        insertAfterButton.setText("");
        deleteCommand.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "<html><head/><body><p>\u0423\u0434\u0430\u043b\u0438\u0442\u044c</p></body></html>", null));
        deleteCommand.setText("");
        backCommandButton.setText("");
        forwardCommandButton.setText("");
        menu.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0424\u0430\u0439\u043b", null));
        menu_2.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041c\u0430\u0448\u0438\u043d\u0430", null));
        menu_3.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u0412\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u0435", null));
        menu_4.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", null));
    } // retranslateUi

}

