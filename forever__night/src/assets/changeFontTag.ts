import {nextTick} from "vue";
import store from "../store";

/**
 * 设置粗体或倾斜体函数
 * @auchor zcl2023.10.5
 * @param elementName 设置标签名称 {'strong'|'em'}
 * @param elseName 其它对斥标签名称 {'em'|'strong'}
 * @example changeFontTag('strong', 'em')
 */
export function changeFontTag(elementName: string, elseName: string){
    nextTick(() => {
        const range = store.state.selection.getRangeAt(0);
        const ELEMENTNAME=elementName.toUpperCase()
        // toLowerCase() //转小写
        const $em = document.createElement(elementName);
        // console.log(range.commonAncestorContainer)
        //如果已经在父元素设置对应标签，则移除对应标签
        if(range.commonAncestorContainer.parentElement.nodeName==ELEMENTNAME){
            let parentElementNow=range.commonAncestorContainer.parentElement
            do{
                $(range.commonAncestorContainer.parentElement).contents().unwrap();
                parentElementNow=parentElementNow.parentElement
            }while (parentElementNow.nodeName==ELEMENTNAME)
            return
        }
        //如果在其他父元素下设置对应标签则移除对应标签，否则设置对应标签
        if(range.commonAncestorContainer.parentElement.nodeName==elseName.toUpperCase()){
            if(range.commonAncestorContainer.parentElement.firstElementChild.nodeName==ELEMENTNAME){
                $(range.commonAncestorContainer.parentElement.firstElementChild).contents().unwrap();
                return
            }
            else{
                range.surroundContents($em);
                return;
            }
        }
        //如果未设置设置对应标签，则设置对应标签，否则移除
        if(!range.commonAncestorContainer.firstElementChild||
            (range.commonAncestorContainer.firstElementChild.nodeName!=ELEMENTNAME &&
                range.commonAncestorContainer.lastElementChild.nodeName!=ELEMENTNAME)) {
            range.surroundContents($em);
        }
        else{
            if(range.commonAncestorContainer.firstElementChild.nodeName==ELEMENTNAME){
                $(range.commonAncestorContainer.firstElementChild).contents().unwrap();
            }
        }
    })
}