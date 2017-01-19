## 欢迎使用Palmap 地图渲染引擎 ---- Nagrand

### version 2.16.32

### 目录结构：

demo --- 存放示例代码  
doc  --- 存放api接口文档和开发者指南  
libs --- SDK的库文件和依赖文件  
resource --- 资源文件，包含lua和media，如何存放这两个文件，请参考开发者指南

### 2.16.32 change log
- 地图比例尺设置功能实现。
- 导航分段详情与动态导航功能初步实现。
- 优化文字碰撞检测机制。
- 手势操作优化。
- 修复地图移动之后,Overlay位置偏移的bug。
- Overlay支持设置floorId属性。

### 2.16.31 change log
- 增加导航经过的连通设施信息内容。
- 修复导航设置超时时间后请求不到导航线的bug。
- 修复地图缩放级别设置无效bug。
- 修复导航经停点位置不准确bug。
- 修复若干崩溃bug，提高稳定性。

### 2.16.30 change log
- 新增地图初始旋转角度获取与设置接口。
-新增获取导航经过的连通设施信息接口。
- 新增POI优先级设置接口，可以用于控制文字显示优先顺序。
- 定位模块数据访问接口更新，增加对IP、UUID、TAG的支持。
- 文字层与公共设施层实现空样式，可以过滤显示特定的POI。
- 网络模块支持http2.0。
- 提高地图加载效率。
- 提高楼层切换效率。
- 修复动画崩溃bug。
-  复ICON渐隐效果打开时，ICON有时无法加载的bug。
- SDK Demo更新。

### 2.16.28 change log
#### features
- 优化文字显示效果
- 数据缓存机制改进
- 新增search接口，实现搜索某个范围内的poi功能，具体使用参考API文档。
- style为"polygon"的lua样式配置方式改进。3D样式时，用户不需要再配置"left_side"、"right_side"、"top_side"属性，默认颜色与"face"一样，之前配置方式仍支持。
- 文字与图标显隐效果实现。样式配置lua中新增效果启用开关属性"enable_fadein"，具体使用请参照SDK包中的test.lua，默认开启。
- 实现文字与图标的物理尺寸配置方式。文字与图标的大小可以使用"px"、"pt"两种单位，"px"表示像素,"pt"表示点，等于1/72英寸。样式配置lua中新增属性"unit"，具体使用请参照SDK包中的test.lua，默认为"px"。
- 修复一些bug。


### 2.16.27 change log
#### features
- 导航线生成算法重构，粗细不再随地图缩放大小改变
- 增加经纬度与墨卡托坐标转换接口
- 优化渲染效率
- 更改 CoordBeContained 接口

### 2.16.26 change log
#### features
- POI边线支持图元方式绘制，可以在lua文件中配置
- 边线对齐可配置，可以在lua文件中配置
- DataSource新增requestMaps多参数版本，支持关键字检索，详情请看API doc
#### fix bugs
- 优化碰撞检测算法
- 内存占用优化

### 2.16.25 change log
#### features
- 支持https，DataSource可以换成``https://api.ipalmap.com/``
#### fix bugs
- 优化线的生成方式，提升渲染效率

### 2.16.24 change log
#### fix bugs
- 优化内存泄露问题
- 优化logo清晰度
- 优化下载icon线程占中过多问题
- 修复个别英文字体库空格丢失问题

### 2.16.23 change log
#### features
- 支持压缩纹理，根据不同GPU支持程度自动转化
- 获取蓝牙Beacon数据库时支持以Filter的方式自定义过滤加入计算点位的方法（请看API文档）
- 支持横竖屏切换
#### fix bugs
- 最大最小缩放接口无效
- 优化同一深度下Z-Fighting的问题，优化显示效果
- 3D顶面线偏移
- 文字锚点与所属平面高度不一致
- 图标加载失败出现小黑点
- 修复地图坐标转换存在的误差情况
- 支持设施和文字的碰撞检测

### 2.16.21 change log
#### features
- 支持文字图标渲染，具体配置方式参考map-style.lua
- 支持搜索POI返回关键字，可以用于高亮显示
- 支持代码修改图标，可以用于高亮图标，改变图标尺寸等

### 2.16.20 change log
#### features
- 支持经停点导航
- 支持自定义背景颜色
- 支持自定义背景图片
- 支持通过参数锁定地图可移动范围

#### fix bugs
- 修复首次加载地图时可能出现Icon无碰撞检测的问题

### 2.16.19 change log
#### features
- 支持通过Feature获取Geometry
- Polygon支持旋转和平移
- 底层代码逻辑优化

### 2.16.17 change log
#### features
- 优化三角形合并逻辑，提高渲染效率
- 优化3D渲染效率
- 请求添加更多的header
- 添加通过自定义字段条件搜索
```
//layerName 图层的名字,Key 比如"category"， value 比如"25000000"
searchFeature(final String layerName, final String key, final Value value)
```
- 提供遍历Geometry点接口


### 2.16.15 change log
#### features
- selectFeatures,返回多个结果
- 3D侧面生成方法优化
- 对LineString顶点生成的数量进行优化，大幅减少
- 重构内部导航实现，导航接口更加稳定
- 样式路径可以在lua中获取到
```
local engine = GetEngine()
local properties = engine.properties
local lua_path = properties["lua_path"]
```

#### fix bugs
- 文字和Icon碰撞检测在屏幕边缘可能会失效

### 2.16.13 change log
#### features
- 添加地图缓存接口
- 定位添加超时和支持轮询模式
- 添加初始化旋转角度接口
- 添加初始化缩放比例的接口

#### fix bugs
- 地图选点BUG
- 修复部分切换楼层发生内存泄露
- 修复导航请求可能无返回
- 修复POI文字如果为空导致崩溃
- 支持所有类型的POI信息作为文字显示的内容
- 修复lua文件无法读取到导致程序无响应的的问题

### 2.16.11 change log
#### features
- 添加长按事件

#### fix bugs
- 导航线为空时，获取最短路径现在不为0
- 字体显示效果优化
- 极小地图缩放异常
- 字体碰撞在一些屏幕上出现错误
- 某些情况下重绘导致相机没有重置成功
- 渲染顺序错乱
- 字体文件路径错误导致第二次启动应用时可能会卡死

### other
- 修改Nagrand-Demo依赖错误

### 2.16.9 change log
#### features
- 修改上次Zoom接口，使用level控制缩放层级，原来的接口失效，不再维护
- DataSource提供了通过一个点查找包含它的所有POI信息
- 支持纹理旋转，详情看resource/lua/test.lua文件
- 3D高度自适应地图大小
- 导航线自适应大小，详情看resource/lua/test.lua文件
- 自定义地图初始化大小
- 调整个别英文字的对齐方式

#### fix bugs
- 重构RequestParam
- 3D状态下字体位置偏移
- ICON旋转角度不一致
- 修复蓝牙定位无法切换楼层
- 销毁WIFI定位导致ANR

### 2.16.7 change log
#### features
- 修改字体的对齐方式
- 字体碰撞检测范围可配置
- 样式配置支持正则匹配规则

#### fix bugs
- 修复Geometry如果为null时导致崩溃的问题
- 修复zoom level会丢失的问题
- ICON 3D高度设置修复

### 2.16.5 change log
#### features
- 支持ICON可被选取
- 支持简单热点图的展示
- 支持空样式（跳过渲染）

### 2.16.3 change log
#### features
- 优化3D ICON显示效果和旋转、移动动画
- 添加导航线截取算法（请参考文档NavigateManager）

#### fix bugs
- 优化快速缩放导致碰撞检测不起作用

### 2.16.1 change log
#### features
- 3D效果侧面可以显示不同颜色，增加立体感，支持lua配置
- 双击地图事件和放大效果，可屏蔽（Android）
- 支持MultiPolygon的渲染
- 3D ICON的支持
- 增加更多的相机动画：zoom、skew、rotate

#### fix bugs
- 清理动态改变样式后对原有合并数据的清理
- 修复相机动画冲突
- 修复蓝牙定位容易发生崩溃

### 2.15.53 change log
#### features
- 新增设置俯仰角度的接口
- 新增获取当前俯仰角度的接口
- 新增动态改变样式的接口（目前只支持改变颜色，未来会支持更多），详见API doc

#### fix bugs
- 修复2D/3D切换出现Z-Fighting
- 修复Android某些机型可能会出现读取到错误的SD卡路径问题
- 修复moveToRect在3D模式下导致不正确的行为
- 跨楼层导航距离提供层高的权重
- 修复设置缩放层次会导致渲染帧数下降
- 修复比例尺计算结果在某些情况下可能会出错
- 修复文字叠加某些字段解析失败

### other
- 添加Demo/quickstart里QuickStart.java点击改变颜色的示例代码

### 2.15.51 change log
#### features
- 新增POI兴趣点文字条件叠加
- 新增导航线方向指示箭头
- 新增支持通过场景ID调取定位数据 
- WIFI定位增加场景id的支持
- 新增相机动画效果 
- 优化3D基本图形的显示效果
- 优化比例尺   

#### fix bugs
- 修复导航线转折点缺口问题
- 修复相机默认最大最小值
- 修复如果字体路径错误可能导致崩溃的问题
- 线条容易出现Z Fighting

### other
- 添加Nagrand Demo工程文件，包含很多功能点的示例代码

### 2.15.49 change log
#### features
- 添加导航线距离接口
- 添加地图指定旋转角度接口
- 添加获取缩放层级，设置最大最小缩放层级接口
- 支持3D显示效果，具体方法请看QuickStart
- 添加了一些数据获取接口

#### fix bugs
- 深度缓冲策略导致显示效果失效
- 3D定位效果合并点策略问题修复，提高3D模式下的渲染效率
- 2D/3D切换不正常
- 修复数据模型获取bool类型崩溃问题
- search类型分类

### other
- 添加QuickStart工程文件