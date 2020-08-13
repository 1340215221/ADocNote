# ADocNote

adoc笔记软件

使用SwingBuilder实现, Groovy和Java混编


项目结构设计
builder groovy直接生成控件类
component 自定义控件类
view 控件业务类
file 文件业务逻辑类
grammar 语法逻辑类
action swing和service的聚合层
frame 主窗口启动类
event 事件触发类
config 构建对象，管理对象依赖
register 注册groovy控件
api 各模块业务聚合层


`todo` 拓展标签
-[ ] 读取文件标题列表
-[ ] 全局保存文件


`todo` 待确认需求
-[ ] 语法块显示效果