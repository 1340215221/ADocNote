package com.rh.note


import com.formdev.flatlaf.FlatDarculaLaf
import com.rh.note.api.GitApi
import com.rh.note.util.GitUtil
import groovy.swing.SwingBuilder

class TestDemo {

    static {
        FlatDarculaLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {
        /**
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                pack: true,
        ){
            builder.panel(layout: new BorderLayout()){
                builder.button(text: '同步',
                        mouseClicked: {
                            builder.dialog(id: 'dialog',
                                    pack: true,
                            ){
                                builder.label(text: '同步项目中',
                                        constraints: BorderLayout.NORTH,
                                )
                                builder.progressBar(id: 'progressBar',
                                        preferredSize: [500, 20],
                                )
                            }
                            builder.dialog.locationRelativeTo = null
                            builder.dialog.visible = true

                            Thread.start {
                                int i = 0
                                while (i < 100) {
                                    builder.progressBar.value = i
                                    i++
                                    Thread.sleep(500)
                                }
                            }
                        }
                )
            }
        }

        builder.frame.locationRelativeTo = null
        builder.frame.visible = true
         */

//        reset()
        if(java.awt.Desktop.isDesktopSupported()){
            try{
                //创建一个URI实例,注意不是URL
                java.net.URI uri=java.net.URI.create("http://www.jb51.net");
                //获取当前系统桌面扩展
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }
            }catch(java.lang.NullPointerException e){
                //此为uri为空时抛出异常
                println "error 1"
            }catch(java.io.IOException e){
                //此为无法获取系统默认浏览器
                println "error 2"
            }
        }
    }

    static void reset() {
        def git = new GitUtil('/my_code/test-jgit')
        git.init()
        git.reset()
    }

    static void pull() {
        def git = new GitApi()
        git.pull('/my_code/test-jgit')
    }
}
