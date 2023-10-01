import {nextTick} from "vue";
import store from "../store";

export function changeContent(node, NODE){
    nextTick(() => {
        const range = store.state.selection.getRangeAt(0);
        // console.log(range.commonAncestorContainer)
        if(range.commonAncestorContainer.parentNode.nodeName==NODE){
            do{
                $(range.commonAncestorContainer.parentNode).contents().unwrap();
            }while (range.commonAncestorContainer.parentNode==node)
            return
        }
        if(!range.commonAncestorContainer.firstElementChild||
            (range.commonAncestorContainer.firstElementChild.nodeName!=NODE&&range.commonAncestorContainer.lastElementChild.nodeName!=NODE)) {
            const $em = document.createElement(node);
            range.surroundContents($em);
        }
        else{
            $(range.commonAncestorContainer.firstElementChild).contents().unwrap();
        }
    })
}