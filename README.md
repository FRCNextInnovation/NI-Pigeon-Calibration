中文简体 | ...

# NI-Pigeon-Calibration

[![Next-Innovation](https://img.shields.io/badge/Next-Innovation-blueviolet?style=flat)](https://github.com/FRCNextInnovation) [![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat)](https://github.com/RichardLitt/standard-readme) [![Lang](https://img.shields.io/badge/Lang-zh--CN-Green?style=flat)]()

本篇文档是一份用于 **Next-Innovation** 电控组开发过程中标定 Pigeon-IMU 参数的指南。

本篇指南包含以下内容:

- 影响 Pigeon-IMU 零飘的主要因素。
- 对 Pigeon-IMU 进行温度标定的步骤。

<img src="./assets/Logo_Purple_Word_Transparent.png">

## 目录

- [NI-Pigeon-Calibration](#NI-Pigeon-Calibration)
  - [影响Pigeon-IMU零飘或测算精准度的因素](#影响Pigeon-IMU零飘或测算精准度的因素)
  - [Pigeon-IMU的温度标定](#Pigeon-IMU的温度标定)

## 影响Pigeon-IMU零飘或测算精准度的因素

- 传感器安装位置是否位于COR(center of rotation: 旋转中心): 
   1. 把机器人抵在一个固定的平面上(比如一面墙)。
   2. 将 Yaw 轴数据归0，并初始化陀螺仪Z轴。
   3. 将机器人按照**0转向**(第2步结束后机器人的朝向)，以最大速度直行约30秒。
   4. 将机器人按照相反的方向，以最大速度直行约30秒或重新抵住平面。
   5. 读取机器人抵在平面时 Yaw 轴的读数和陀螺仪Z轴的读数。
   6. 如果 Yaw 轴数据不正确，陀螺仪 Z 轴数据正确，则 Pigeon 放置位置不处于COR，请校准位置。
- 避免IMU周围存在磁性/铁磁性材料。
- 机器人运行过程中的剧烈振动会影响到Pigeon的测算精度，可以使用橡胶塔或者橡胶索环来缓震Pigeon。
- 温度变化会极大程度上影响陀螺仪的零飘值，同时也在一定程度上会对加速计和地磁计的测量结果造成影响。最好且唯一的解决方法是为Pigeon重新进行温度标定，以调整温度补偿算法的参数。详情请看[Pigeon-IMU的温度标定](#Pigeon-IMU的温度标定)。

## Pigeon-IMU的温度标定
1. :warning:**请将Pigeon放置于平整的表面**。

2. 使用 VSCode 打开同目录下的 "**Pigeon-Temperature-Calibration-Code**" 项目文件夹，并烧录代码。此处不再赘述配置项目文件夹的具体方法。

3. 打开 Driver Station 与 ShuffleBoard，观察 ShuffleBoard 中 Pigeon 的温度曲线，使其**温度冷却至30℃**以下。

4. 待 Pigeon 冷却到30℃以下后，**通过 Driver Station 使机器人进入 "Test" 模式**，然后使用日光灯照射 Pigeon 背面，之后程序将开始检测**温度变化对 Pigeon 解算出的 Fused Heading 的值产生的影响**(如图为 Pigeon 背面，通过日光灯照射，使 Pigeon 的表面温度依次经过30℃和60℃)。

   <img src="./assets/pigeon-lamp.png" style="zoom: 33%;" />

5. 待检测结束后，令 Pigeon 冷却至30℃以下。

6. 待 Pigeon 冷却到30℃以下后，**通过 Driver Station 使机器人进入 "Autonomous" 模式**，之后 Pigeon 将进入到温度标定模式。之后 Pigeon 将会进行一次比正常情况下耗时更长的启动，待启动后，Pigeon 的所有 Led 灯将同时亮起，并发出比往常更亮的橙色光，之后可以开始对 Pigeon 进行温度标定。

7. 使用日光灯照射 Pigeon 背面，请注意全程不要移动和触碰 Pigeon ，一旦 Pigeon 检测到温度的变化量满足标定结束的条件，Led 灯将开始以绿色光闪烁，之后 Pigeon 将会重启，**重启后温度标定结束**。

8. 待 Pigeon 冷却到30℃以下后，**再次通过 Driver Station 使机器人进入 "Test" 模式**，然后使用日光灯照射 Pigeon 背面，检测温度标定后 Pigeon 结算出的Fused Heading值随温度的变化情况，即可验证温度标定对零飘带来的改善。

## 维护者

[@ljy1992](https://github.com/ljy1992) [@Rocky_](https://github.com/RockyXRQ)

<img src="./assets/maintainers.png"/>