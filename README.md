中文简体 | ...

# NI-Pigeon-Calibration

[![Next-Innovation](https://img.shields.io/badge/Next-Innovation-blueviolet?style=flat)](https://github.com/FRCNextInnovation) [![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat)](https://github.com/RichardLitt/standard-readme) [![Lang](https://img.shields.io/badge/Lang-zh--CN-Green?style=flat)]()

本篇文档是一份用于 **Next-Innovation** 电控组开发过程中标定Pigeon-IMU参数的指南。

本篇指南包含以下内容:

- 影响Pigeon-IMU零飘的主要因素。
- 对Pigeon-IMU进行温度标定的步骤。

<img src="./assets/Logo_Purple_Word_Transparent.png">

## 目录

- [NI-Pigeon-Calibration](#NI-Pigeon-Calibration)
  - [影响Pigeon-IMU零飘或测算精准度的因素](@影响Pigeon-IMU零飘或测算精准度的因素)
  - [Pigeon-IMU的温度标定](#Pigeon-IMU的温度标定)

## 影响Pigeon-IMU零飘或测算精准度的因素

- 传感器安装位置是否位于COR(center of rotation: 旋转中心): 
   1. 把机器人抵在一个固定的平面上(比如一面墙)。
   2. 将Yaw轴数据归0，并初始化陀螺仪Z轴。
   3. 将机器人按照0转向(第2步结束后机器人的朝向)，以最大速度直行约30秒。
   4. 将机器人按照相反的方向，以最大速度直行约30秒或重新抵住平面。
   5. 读取机器人抵在平面时Yaw轴的读数和陀螺仪Z轴的读数。
   6. 如果Yaw轴数据不正确，陀螺仪Z轴数据正确，则Pigeon放置位置不处于COR，请校准位置。
- 避免IMU周围存在磁性/铁磁性材料。
- 机器人运行过程中的剧烈振动会影响到Pigeon的测算精度，可以使用橡胶塔或者橡胶索环来缓震Pigeon。
- 温度变化会极大程度上影响陀螺仪的零飘值，同时也在一定程度上会对加速计和地磁计的测量结果造成影响。最好且唯一的解决方法是为Pigeon重新进行温度标定，以调整温度补偿算法的参数。详情请看[Pigeon-IMU的温度标定](#Pigeon-IMU的温度标定)。

## Pigeon-IMU的温度标定