namespace java cc.blisscorp.event.game.thrift.gen

struct TTransaction{
    1: i64 userId,
    2: string userName,
    3: i64 amount,
    4: i32 gameId
}

service TEventGameService{
    bool ping(),
    TTransaction saveTrans(1: TTransaction trans)
}
