


// 如果是多处理机系统，添加0xF0前缀
#define LOCK_IF_MP(mp) __asm cmp mp, 0  \
                       __asm je L0      \
                       __asm _emit 0xF0 \
                       __asm L0:

inline jint Atomic::cmpxchg (jint exchange_value, volatile jint* dest, jint compare_value) {
    // 判断系统是否为多处理机系统(MP MultiProcessor)
    int mp = os::is_MP();
    __asm {
        mov edx, dest
        mov ecx, exchange_value
        mov eax, compare_value
        LOCK_IF_MP(mp)
        cmpxchg dword ptr [edx], ecx
    }
}
