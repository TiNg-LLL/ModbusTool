2020.08.30

V0.0.1  
1、完成主窗口；  
2、完成功能性弹窗；  
3、完成一定布局；

V0.1.0  
1、完成COM端口选择功能，实现端口参数设置功能；  
2、建立Bodbus，实现连接；  
3、实现写入读取功能；  
4、实现修改地址功能；  

V0.1.1  
1、准备添加信捷PLC D寄存器双地址数据处理；  

V0.2.0  
1、完成信捷PLC D寄存器双地址数据处理；  
2、增加DataTreat类 对十进制转二进制 二进制转十进制进行处理；  

V0.2.1  
1、点动/切换功能未实现；  

V0.3.0  
1、点动/切换功能已实现 getJButton().getMouseListeners()[0] 不能删除 是按钮自带的监听；  
2、可通过boolean控制功能组显示或隐藏；

V0.3.1  
1、完成基础功能  功能第一版

V0.3.2  
1、增加材料厚度  厚度换算脉冲量；  
2、增加步进电机脉冲细分设置；

V0.3.3  
1、增加物理速比设置；

V0.3.5  
1、微调显示布局；  
2、更改为使用JDK1.8； 

V0.3.6  
1、实现了窗口左上角图标；

V0.3.7  
1、左下角信息显示增加箭头代表刷新速率；

V0.3.8  
1、左上角图标地址小调整；

V0.3.9  
1、优化信息通知；

V0.4.0  
1、完成数据转换 输入数据改为mm单位  自动转换为相应数值通过Modbus，满足浮点类型 但可能还存在精度问题；  
2、完成权限密码登入功能；

V0.4.1  
1、细微调整；

V0.4.2  
1、增加X端口支持；

V0.4.3  
1、小改版，密码输入支持字母；  
2、下一版本尝试将默认参数写入配置文件；

V0.5.0  
1、完成配置文件应用，现在可以在address.properties中直接修改初始默认值；  
2、修复在V0.4.2版本中加入X线圈后，出现的修改线圈地址无效的bug；

V0.5.1  
1、细微调整，更换图标；

V0.5.2  
1、窗口细微调整；

V0.5.5  
1、增加在配置文件默认端口选择；

V0.5.6  
1、注释调整；  

V0.5.8  
1、增加过期代码；

V0.5.9  
1、增加当前位置；  
2、调整无权限下显示内容；

V0.6.0  
1、调整当前位置读取数值为mm；  
2、修复参数设置隐藏bug；

V0.6.2  
1、使用exe4j转换后，窗口比在idea里运行各方向大15，未找到原因；

V0.6.5  
1、重构读取线程，将各个值独立为单独线程实现；