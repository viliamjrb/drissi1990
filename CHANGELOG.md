# 版本日志

# 2.8.6

### 新增功能

1. 项目运行模式新增 Dsl，配合脚本模版实现自定义项目管理
2. 【server】新增快捷恢复 h2 奔溃数据（启动参数添加：`--recover:h2db`）（感谢@大土豆）
3. 【server】邮箱配置新增超时时间配置（感谢@Y.）
4. 新增快速安装导入节点（插件端）
5. 【server】新增服务端脚本模版（区别于节点脚本模版）

### 解决BUG、优化功能

1. 【server】数据库备份自定义表显示中文描述
2. 【server】配置 ip 白名单判断是否合法,并且支持 ip/掩码位： `192.168.1.0/24` 格式（感谢@skyou）
3. 【server】脚本模版独立菜单
4. 【server】执行升级前自动执行备份数据逻辑（保障数据稳定）
5. 【server】节点分发项目状态显示节点 ID 更正为节点名称 （感谢@hu丶向...🤡）
6. 【server】升级 jgit 到 5.13

------

# 2.8.5 (2022-01-14)

### 新增功能

1. 新增保留旧包个数配置参数`system.oldJarsCount`

### 解决BUG、优化功能

1. 【server】恢复删除脚本模版、ssh 命令模版未删除日志数据
2. 【server】项目副本集没有显示运行端口+进程ID（感谢@ʟᴊx💎💎）
3. 【server】ssh 发布清除产物目录忽略`No such file`异常
4. 【server】节点升级中远程下载插件包存储路径更改，并优化更新包后页面显示问题（感谢@hu丶向...🤡）
5. 脚本模版新增描述字段（感谢@ʟᴊx💎💎）
6. 在线升级取消重复 jar 包判断,改为自动重命名（感谢@大土豆）
7. 项目文件管理调整为支持清空当前目录（感谢@ʟᴊx💎💎）
8. 【server】ssh 列表安装节点按钮判断 java 环境

------

# 2.8.4 (2022-01-06)

### 新增功能

### 解决BUG、优化功能

1. 【server】数据库新增连接池配置参数（感谢@ʟᴊx💎💎）
2. 【agent】fix：解压 tar.gz 文件
3. 【server】fix：恢复导航助手不能正常显示问题
4. 【agent】fix 插件端首页不能正常关闭进程（感谢@平安茹意）
5. 【agent】fix 插件端首页支持自定义进程名（感谢@者羽）
6. 【server】恢复节点、构建分组字段（感谢@ʟᴊx💎💎）
7. 【server】后端默认开启 gzip 提升访问速度（感谢@小夜）

------

# 2.8.3 (2021-12-30)

### 新增功能

1. 脚本模版新增日志管理
2. 【server】ssh 文件管理新增导入压缩包自动解压（感谢@刘志远）
3. 脚本模版新增定时执行（感谢@大土豆）

### 解决BUG、优化功能

1. 【server】节点分发数据新增状态字段,启动程序时候触发恢复异常数据
2. 【server】定时执行相关 cron 表达式输入提示示例数据
3. 【server】节点升级菜单取消，整合到【系统管理】-> 【在线升级】中
4. 【server】邮箱配置优化,新增 smtp、port 示例数据，取消 SSL 端口字段（感谢@💎ℳ๓₯㎕斌💎💘）
5. 【server】fix：新增用户无法正常输入bug（感谢@在路上）
6. 【server】fix：ssh 命令执行记录菜单没有独立显示问题（感谢@刘志远）

------

# 2.8.2 (2021-12-24)

### 新增功能

1. 【server】仓库新增导入 Gitee、Github 仓库信息（感谢@💎ℳ๓₯㎕斌💎💘）
2. 【server】ssh 新增命令模版、可以用于批量执行命令脚本
3. 新增配置属性 `system.timerMatchSecond` 调度(定时任务)是否开启秒级匹配（感谢@大土豆）
4. 缓存管理新增清除旧版本程序包功能
5. 【server】用户权限新增绑定工作空间权限（指定工作空间的修改、删除、上传、执行等权限）

### 解决BUG、优化功能

1. 【server】nginx 列表显示不全，无法滚动问题（感谢@）
2. 【server】独立节点分发显示节点名称（感谢@奥特曼打猪）
3. 【server】用户ID（登录名）支持邮箱格式（感谢@陈力）
4. 【server】优化清除构建和删除构建时候删除相关文件操作（使用系统命令快速删除）（感谢@大土豆、[Gitee PR](https://gitee.com/dromara/Jpom/pulls/155) ）
5. 【server】项目搜索菜单名变更为项目列表
6. 【server】调整自动清理日志数据逻辑、默认保留日志数据条数修改为 `10000`
7. 【server】脚本模版在服务端统一查看、编辑、执行（感谢@ʟᴊx）
8. 【server】ssh 私钥支持配置文件和加载用户目录下的私钥文件
9. 【server】初始化超级管理员不能使用 `demo` 关键词（感谢@A.Nevermore）

> 注意：
> 1. 已经添加的用户重新绑定工作空间权限（默认没有工作空间操作权限）

------

# 2.8.1  (2021-12-17)

### 新增功能

1. 节点缓存页面新增定时作业列表
2. 节点首页新增其他类型进程监控（感谢@大土豆）
3. 构建中的项目发布新增差异发布（多文件项目或者网络不佳情况只发布有变化的文件节省项目发布时间）（感谢@大灰灰）

### 解决BUG、优化功能

1. 【server】解决节点未配置监控周期接口报错+页面循环提示（感谢@周健全）
2. Windows 无法关闭 Jpom 程序（感谢@……）
3. 【server】恢复项目搜索、节点分发项目的文件、控制管理无法正常使用（感谢@刘志远）
4. 脚本文件提示内容取消中文，修改为英文
5. 【agent】新增检查 jps 命令执行是否存在异常,异常则提示用户（感谢@……）
6. 部分控制台输出日志调整为英文
7. 【server】优化 ssh 安装插件端,不输入节点ID、没有配置权限报错（感谢@大土豆）
8. 【agent】恢复项目 `JavaExtDirsCp` 模式加载非 Jar 文件问题（感谢@大灰灰）
9. 升级 SpringBoot 版本 2.6.1
10. 【agent】恢复项目配置 webhook 后无法关闭进程的情况（感谢@大土豆）
11. 【server】ssh 命令日志低版本字段类型文件恢复（感谢@大土豆）
12. 【server】释放独立分发和删除分发项目提示更明确（感谢@周健全）
13. 【server】恢复自动导入节点异常（感谢@平安茹意）
14. 恢复节点密码包含特殊字符时节点控制台等相关功能无法正常使用问题（感谢@魔方技术-李广生）
15. 恢复解锁节点没有指定到对应的工作空间（感谢@周健全）

------

# 2.8.0  (2021-12-14)

### 新增功能

1. 【server】新增工作空间概念（取代角色相关）【系统将自动创建默认工作空间、默认工作空间是不能删除】
2. 【server】用户新增可以配置管理员功能【管理员可以管理系统中的账号、系统管理等功能（除升级系统、导入数据外）】
3. 【server】新增超级管理员（第一次初始化系统等账号为超级管理员），超级可以拥有整个系统权限不受任何限制
4. 【server】列表数据都新增分页、搜索、排序功能（搜索字段、排序字段正在完善补充中）
5. 【server】新增通过命令行重置 IP 白名单配置参数 `--rest:ip_config`
6. 【server】新增通过命令行重置超级管理员参数 `--rest:super_user_pwd`
7. 【server】新增通过命令行重新加载数据库初始化操作参数 `--rest:load_init_db`
8. 【server】构建新增`本地命令`发布方式 用户在服务端执行相关命令进行发布操作
9. 【server】发布命令（SSH发布命令、本地命令）支持变量替换：`#{BUILD_ID}`、`#{BUILD_NAME}`、`#{BUILD_RESULT_FILE}`、`#{BUILD_NUMBER_ID}`
10. 【server】新增自动备份全量数据配置 `db.autoBackupIntervalDay` 默认一天备份一次,执行备份时间 凌晨0点或者中午12点
11. 【agent】项目的 webhook 新增项目启动成功后通知，并且参数新增 `type` 指包括：`beforeStop`,`start`,`stop`,`beforeRestart`
12. 【agent】项目新增自启动配置项,在 agent 启动时候检查对应项目是否启动，未启动执行启动逻辑 [Gitee issues I4IJFK](https://gitee.com/dromara/Jpom/issues/I4IJFK)
13. 【server】构建新增 webhook，实时通知构建进度
14. 【server】节点分发新增分发间隔时间配置
15. 新增控制台日志配置数据 `consoleLog.charset` 避免部分服务器执行命令响应乱码 （感谢@……）
16. 【server】构建触发器新增批量触发 [Gitee issues I4A37G](https://gitee.com/dromara/Jpom/issues/I4A37G)
17. 【server】构建支持定时触发 [Gitee issues I4FY5C](https://gitee.com/dromara/Jpom/issues/I4FY5C)

### 解决BUG、优化功能

1. 【server】用户账号、节点、SSH、监控、节点分发等数据由 JSON 文件转存 h2
2. 【server】取消节点、构建分组字段
3. 【server】取消角色概念（新增工作空间取代）
4. 【server】操作监控数据由于数据字段不兼容将不自动升级需要用户重新配置
5. 【server】系统参数相关配置都由 JSON 转存 h2（邮箱配置、IP白名单、节点分发白名单、节点升级）
6. 【server】关联节点项目支持绑定单个节点不同项目
7. 【server】构建触发器新增跟随创建用户走，历史 url 将失效,需要重新生成
8. 【server】仓库`假删`功能下线，已经删除的仓库将恢复正常（假删功能后续重新开发）
9. 【agent】项目数据新增工作空间字段、取消分组字段
10. 【server】节点 ID 取消用户自定义采用系统生成
11. 【server】优化节点弹窗和菜单折叠页面布局
12. 【server】编辑节点、SSH、邮箱配置不回显密码字段
13. 【server】优化 SSH 终端不能自动换行问题
14. 【agent】脚本模版新增工作空间字段、列表数据并缓存到服务端、新增执行日志
15. 【server】优化批量操作项目启动、关闭、重启交互
16. 【agent】恢复在线升级插件端提示 [Agent-.jar] 已经存在啦,需要手动到服务器去上传新包
17. 自动注册对节点需要手动绑定工作空间后,节点才能正常使用 (感谢@💎ℳ๓₯㎕斌💎💘)

> 特别感谢：Jpom 社区测试组成员【🐠】、【ʟᴊx】、【hu丶向...🤡】等参与内测的人员

> 注意：
>
> 【特别说明】：分组字段将失效，目前所有数据在升级后都将默认跟随`默认工作空间`。
>
> 1: 升级该版本会自动将原 JSON 文件数据转存到 h2 中，如果转存成功旧数据文件将自动移动到数据目录中的 `backup_old_data` 文件夹中
>
> 2: 升级过程请注意控制台日志是否出现异常
>
> 3: 操作监控数据由于数据字段不兼容将不自动升级需要用户重新配置
>
> 4: 监控报警记录、构建记录、操作记录由于字段兼容问题存在部分字段为空的情况
>
> 5：非超级管理员用户会出现由于未分配工作空间不能正常登录或者不能使用的情况，需要分配工作空间才能登录
>
> 6: 用户绑定工作空间后，用户在对应工作空间下可以创建、修改、删除对应的数据（包括但不限于管理节点）
>
> 7: 此次升级启动耗时可能需要2分钟以上（耗时根据数据量来决定），请耐心等待和观察控制台日志输出
>
> 8: 一个节点建议不要被多个服务端绑定（可能出现数据工作空间错乱情况）
------

# 2.7.3 (2021-12-02)

### 新增功能

1. 【server】新增自定义系统网页标题配置`jpom.name`
2. 【server】新增自定义系统网页 logo 配置`jpom.logoFile`
3. 【server】新增自定义系统登录页面标题配置`jpom.loginTitle`
4. 【server】新增自定义系统 logo 文字标题配置`jpom.subTitle`
5. 新增在线下载最新版本更新包功能（在线检测最新版本）
6. 【server】新增菜单`系统管理-数据库备份`，支持 Jpom 使用的 H2 数据库备份、还原

### 解决BUG、优化功能

1. 【server】恢复构建产物为匹配符无法正常发布问题（感谢@Kay）
2. 【server】恢复在线升级页面在二级路径下无法使用的问题 (感谢@hu丶向...🤡)
3. 【server】恢复构建执行命令阻塞问题（感谢@小猿同学）
4. 【server】恢复限制 IP 访问和插件端授权信息不正确状态码冲突（感谢@小龙、@大灰灰）
5. 取消 tools.jar 依赖
6. 【server】优化初始化数据库流程，避免多次执行相同修改，节省启动时间
7. 【fix】恢复项目副本集乱码（感谢@ʟᴊx）
8. 【server】添加在线升级完成后的回调提示
9. 【server】ssh安装节点按钮动态显示
10. 【server】恢复构建信息中脚本过长无法构建的bug（感谢@Dream）
11. 在网页的编辑器中修改配置文件时兼容tab键（感谢@Dream）

> 取消 tools.jar 依赖后，Java 项目状态监控使用 `jps` 命令实现

------

# 2.7.2 (fix)

### 新增功能

### 解决BUG、优化功能

1. 【agent】解决 nginx 编辑配置文件 url 编码问题
3. 【server】新增配置构建命令支持不检测删除命令 `build.checkDeleteCommand` (感谢@Dream)

------

# 2.7.1 (fix)

### 新增功能

### 解决BUG、优化功能

1. 解决插件端请求参数 url 编码无法解析问题（感谢@知识就是力量）
2. 【agent】项目文件夹为空不再提示错误信息
3. 【server】fix 编辑构建选择 ssh 发布无法保存 （感谢 @Peision [Gitee issues I4CQWA](https://gitee.com/dromara/Jpom/issues/I4CQWA) ）
4. 【server】fix ssh 终端未配置禁用命令不能输入空格问题

------

# 2.7.0 (beta)

### 新增功能

1. **【server】构建中的仓库独立管理**
2. **【server】构建信息存储方式调整为 h2 数据库，不再存储到 json 文件中**
3. **【server】构建触发器地址变更**
4. 【agent】新增文件管理中允许编辑的文件后缀，以及对应后缀的文件编码
5. 项目文件管理中新增编辑按钮，支持编辑文本文件（ 新版本 UI 同步新增该功能）
6. 程序启动输出默认 IP 地址和当前运行端口信息
7. bat 管理命令（windows）启动后输出日志文件,方便排查当前启动情况
8. 【server】上传文件到插件端（节点）超时配置独立,采用 server 端全局配置,配置参数 `node.uploadFileTimeOut`
   （感谢 @LW 根据 Gitee  [issues I3O8YE](https://gitee.com/dromara/Jpom/issues/I3O8YE) ）
9. 【server】角色新增添加权限配置 （感谢@misaka [Gitee pr](https://gitee.com/dromara/Jpom/pulls/141) ）
10. 【server】节点升级上传新包成功后删除历史包
11. 【server】新版本 UI 菜单系统管理、节点升级只有系统管理员可见
12. 【server】新版本 UI 脚本模板同步添加执行参数（感谢@轻描淡写 [Gitee issues I43G4B](https://gitee.com/dromara/Jpom/issues/I43G4B) ）
13. 【server】新版本 UI 同步添加 common.js
14. 【agent】项目文件管理新增下载远程文件功能
15. 【agent】节点首页监控新增实际使用内存占比（linux系统） （感谢@大灰灰）
16. 【server】ssh 新增操作记录（方便查看执行历史回溯操作）
17. 【server】新增 h2 控制台配置属性,基于 SpringBoot,配置参数`spring.h2.console.enabled`
18. 【server】节点分发支持下载远程文件 （感谢@落泪归枫 [Gitee issues I1LM27](https://gitee.com/dromara/Jpom/issues/I1LM27) ）
19. 【server】节点分发支持 file 类型项目
20. 【agent】项目新增配置日志文件输出到指定目录
21. 【server】构建产物目录支持通配符`AntPathMatcher`模式 （感谢@saysay [Gitee issues I455FM](https://gitee.com/dromara/Jpom/issues/I455FM) ）
22. 【server】新增 h2 数据库缓存大小配置 [CACHE_SIZE](http://www.h2database.com/html/features.html#cache_settings) `db.cacheSize
23. 【server】构建触发器新增延迟执行参数（感谢@Steve.Liu）
24. 【server】增加全局项目搜索功能
25. 【agent】项目增加批量启动关闭重启
26. 【server】节点分发文件支持上传非压缩包（感谢@Sam、風中飛絮 [Gitee issues I3YNA5](https://gitee.com/dromara/Jpom/issues/I3YNA5) ）
27. 【server】nginx 二级代理无法访问（感谢@hu丶向...🤡）
28. 【server】ssh文件管理新增在线编辑（感谢@嗳啨 [Gitee issues I4ADTA](https://gitee.com/dromara/Jpom/issues/I4ADTA) ）
29. 在线升级支持上传 zip 包自动解析（感谢@Sam）
30. 【server】ssh 安装插件端新增等待次数配置（感谢@hu丶向...🤡）
31. 【server】新增前端接口请求超时配置 `jpom.webApiTimeOut`（感谢@hu丶向...🤡）
32. 【server】构建支持 tag 通配符 （感谢@落泪归枫 [Gitee issues I1LM1V](https://gitee.com/dromara/Jpom/issues/I1LM1V) ）

### 解决BUG、优化功能

1. 【server】添加节点时候限制超时时间，避免配置错误一直等待情况
2. 【server】优化限制 IP 白名单相关判断，避免手动修改错误后一直限制访问
3. 【server】添加 QQ 邮箱配置参照说明 [QQ邮箱官方文档](https://service.mail.qq.com/cgi-bin/help?subtype=1&&no=369&&id=28)
4. 【server】fix: 删除临时文件出现 `AccessDeniedException` 更新文件权限为可读（取消只读权限）
5. 【server】拉取 GIT 代码根据仓库路径添加 `synchronized`
6. 【server】节点管理页面支持刷新当前节点页面（刷新不再回到首页）
7. 【server】 jpom-service.sh 文件加载环境变量修改为 判断模式
8. 【agent】fix: windows 环境保存配置文件错误问题
9. 【agent】fix: 在线升级页面在没有配置白名单时候无法显示节点信息
10. 【server】ssh 快捷安装插件端检查配置文件不在使用 SpringBoot 非 public 工具类
11. 【server】请求节点发生异常打印具体堆栈、接口异常拦截器里面默认不打印堆栈 （根据 Gitee  [issues I3O8YE](https://gitee.com/dromara/Jpom/issues/I3O8YE) ）
12. 【server】节点升级中偶尔出现无法获取到对应的版本信息问题（感谢@misaka Gitee issues [I41TDY](https://gitee.com/dromara/Jpom/issues/I41TDY) ）
13. 本地运行数据目录位置改为`${user.home}/jpom/xxxx`、日志路径改为项目模块下
14. 【agent】升级 `commons-compress` 依赖 （来自 GitHub [advisories](https://github.com/advisories) ）
15. agent 和 server 间的 websocket 鉴权调整
16. 【server】update: 刷新整个页面的时候重新加载菜单
17. 历史监控图表查询报时间格式化错误(字符串工具类) （感谢@misaka [Gitee pr](https://gitee.com/dromara/Jpom/pulls/142) ）
18. 【agent】nginx 配置文件取消强制检测 server 节点
19. 【server】仓库密码改为隐藏
20. 解决退出登录验证码没有刷新问题 （感谢群友：Steve.Liu）
21. 【agent】节点分发清空发布无效（感谢@Sam）
22. 【server】编写分发项目时，当分发节点做替换、新增的操作后，点击确认，控制台报错（感谢@tan90°）

> 【特别声明】当前版本 仓库和构建并没有接入动态数据权限，如果对权限敏感的用户建议等待下一个版本优化权限后再升级（如有疑问可以微信群沟通）

> 注意1：由于构建信息全部存储到 h2 数据库中，之前到构建信息会自动同步，在升级后到第一次启动需观察控制台信息，启动成功后请检查构建信息，仓库信息是否同步正确
>
> 注意2：构建的触发器地址有更新，需要重新获取触发器地址
>
> 注意3：升级到该版本需要保证 agent、server 都保持同步，如果只升级 server 会出现项目控制台等功能无法正常使用
>
> 注意4：升级 2.7.x 后不建议降级操作,会涉及到数据不兼容到情况

------

# 2.6.4-patch

### 新增功能

### 解决BUG、优化功能

1. 【server】构建触发器新增延迟执行参数（感谢@Steve.Liu）
2. 【server】数据库字段类型超大的 varchar 改为 CLOB（感谢@Alex）
3. 【server】获取仓库分支方式修改（避免大仓库执行时间太长）（感谢@自作多情）

------

# 2.6.3-patch

### 新增功能

### 解决BUG、优化功能

1. 【agent】mac 进程号转换问题恢复
2. 【server】节点分发的项目白名单路径回显错误（感谢@tan90°）
3. 【agent】自定义日志路径自动创建（感谢@tan90°）

------

# 2.6.2-patch

### 新增功能

### 解决BUG、优化功能

1. 【server】清除构建目录失败（感谢@大灰灰）
2. 【server】fix: 在线升级页面在没有配置白名单时候无法显示节点信息
3. 【agent】fix: windows 环境保存配置文件错误问题
4. 【agent】升级 commons-compress 依赖 （来自 GitHub advisories ）
5. 【server】优化限制 IP 白名单相关判断，避免手动修改错误后一直限制访问

------

# 2.6.1-patch

### 新增功能

### 解决BUG、优化功能

1. 【agent】 当自定义配置授权信息后增加控制台输出信息,避免用户无感（感谢@南）
2. 【server】增加构建日志表构建命令字段长度，变更后长度为5000
3. 【server】调整编辑构建弹窗布局
4. 【server】ssh 发布命令调整为 sh 命令统一执行,避免类似 `nohup` 一直阻塞不响应
5. 【server】拦截器文件权限异常,提醒检查目录权限

------

# 2.6.0-beta

### 新增功能

1. 【server】新增配置 h2 数据账号密码参数（注意之前已经存在的数据不能直接配置、会出现登录不成功情况）
2. 【agent】项目新增配置控制台日志输出目录 （感谢@落泪归枫  [Gitee I22O4N](https://gitee.com/dromara/Jpom/issues/I22O4N)）
3. 【server】新增配置 jwt token 签名 key 参数
4. 【server】ssh 新增配置禁止执行的命令,避免执行高风险命令
5. 【server】构建发布方式为 ssh 检查发布命令是否包含禁止执行的命令
6. 【server】新增 ssh 执行命令初始化环境变量配置 `ssh.initEnv`

### 解决BUG、优化功能

1. 【agent】 恢复 nginx 重载判断问题（@大灰灰大 码云 issue [I40UE7](https://gitee.com/dromara/Jpom/issues/I40UE7) ）
2. 【server】恢复 ssh 上传文件时候不会自动创建多级文件夹（@大灰灰大）
3. 【server】角色动态权限显示分组
4. 【agent】 新增 stop 项目等待进程关闭时间配置 `project.stopWaitTime`、停止项目输出 kill 执行结果
5. bat 管理命令更新环境变量，避免部分服务器出现无法找到 taskkill 命令（ 感谢@Sunny°晴天、[@zt0330](https://gitee.com/zt0330) ）
6. 升级SpringBoot、Hutool等 第三方依赖版本
7. 去掉旧版本 ui (thymeleaf、layui)
8. 【server】fix： ssh 分发执行命令找不到环境变量问题
9. 【server】在线升级显示打包时间、并发执行分发 jar 包、部分逻辑优化
10. 【server】 构建历史增加下载构建产物按钮（感谢@房东的喵。）
11. 【server】项目控制台新增心跳消息，避免超过一定时间后无法操作的情况
12. 【server】ssh 新增心跳消息，避免超过一定时间后无法操作的情况
13. 【server】系统缓存中的文件占用空间大小调整为定时更新（10分钟）
14. 【server】修复 bug：分发列表页面点击【创建分发项目】按钮之后不能正常显示【分发节点】感谢 @xingenhi [点击查看提交记录](https://gitee.com/dromara/Jpom/commit/bd38528fbd3067d220b7569f08449d7796e07c74) [@Hotstrip](https://gitee.com/hotstrip)
15. 【server】fix: 编辑管理员时用户名不可修改
16. 【server】折叠显示部分列表操作按钮（减少误操作）

> 注意：当前版本为 beta 版本。项目中升级了较多依赖版本、新增了部分重要配置（建议确认好后再配置）.如果大家在升级后使用中发现任何问题请及时到微信群反馈,我们会尽快协助排查解决
>
> 1. 如果是已经安装 Jpom、升级到当前版本请勿直接配置数据库账号密码,如果需要配置请手动连接数据库人工修改密码后再配置

------

# 2.5.0 ~ 2.5.2 版本日志

[https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.5.x.md](https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.5.x.md)

-----------------------------------------------------------

# 2.4.0 ~ 2.4.9 版本日志

[https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.4.x.md](https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.4.x.md)

-----------------------------------------------------------

# 2.3.1 ~ 2.3.2 版本日志

[https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.3.x.md](https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.3.x.md)

-----------------------------------------------------------

# 2.0 ~ 2.2 版本日志

[https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.x.md](https://gitee.com/dromara/Jpom/blob/master/docs/changelog/2.x.md)