# Nightbot
基本上就是 [Nightbot](https://github.com/BillYang2016/nightbot) 的翻版

使用Kotlin编写以原生支持mirai，配置文件支持的可自定义也更多。
可以按照睡觉/清醒时长任意指定消息内容，而且可以从多条消息中随机。同时也可以自定义连续睡觉/起床两次的消息

## 如何安装
把jar丢到plugins文件夹里就行，没上mcl

## 配置文件范例
```yaml
doNotUseAllBots: false #是否对所有BOT账号启用该插件
useBotList: [] #启用插件的BOT账号
useWhiteList: false #白名单
useBlackList: false #黑名单
whiteList: [] #启用BOT功能的群聊群号
blackList: [] #不启用BOT功能的群聊群号
morningRegEx: 
  - '^早$'
  - 早上好
  - 早安
  - 哦哈哟
nightRegEx: 
  - '^晚安$'
  - 眠了
morningPromptsByHour: 
  0...3: 
    - 您就是阴间作息大师？还是说你在地球的另一边？
    - 这个点起床是不是有点那个什么大病？
  3...5: 
    - 这么早的么？
  5...7: 
    - 早上好！起的真早啊！
  8...9: 
    - 看啊，这标准的社畜起床时间！
  9...12: 
    - 真是个睡懒觉的好季节啊。
  12...24: 
    - 您就是阴间作息大师？还是说你在地球的另一边？
nightPromptsByHour: 
  0...3: 
    - '加班/熬夜学习辛苦了 晚安。'
    - 这个点睡觉的人一定没有性生活吧（）
  3...7: 
    - 你这个样子是要猝死的！
  7...12: 
    - 您就是阴间作息大师。
  12...14: 
    - 这个点睡觉的人一定打算睡觉吧，你把我整不会了。
  14...20: 
    - 似乎还有点早，要不再刷会番？
  20...24: 
    - 晚安，早睡早起身体好哦。
  23...0: 
    - 这个点睡觉的人一定没有性生活吧（）
morningPromptsByDuration: 
  0...24: 
    - '这次一共睡了$hour!'
nightPromptsByDuration: 
  0...24: 
    - '今天你清醒了$hour'
doubleMorningPrompts: 
  0...24: 
    - 你搁这儿仰卧起坐呢？
doubleNightPrompts: 
  0...24: 
    - 你是在梦里又睡了一觉吗？
```
