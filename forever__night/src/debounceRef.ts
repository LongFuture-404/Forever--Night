import { customRef } from 'vue';

//适用于普通input，不符合组件input
export function useDebounceRef(value, delay) {
    let timer;
    return customRef((track, trigger) => {
        return {
            get() {
                // 所有依赖收集 track
                track();
                return value;
            },
            set(newValue) {
                clearTimeout(timer);
                timer = setTimeout(() => {
                    value = newValue;
                    // 更新触发 trigger
                    trigger();
                }, delay);
            },
        };
    });
}

export default useDebounceRef;