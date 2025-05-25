"use client"

import './MarkdownShortcuts.scss'

import React from "react"
import {useEditor, EditorContent, type JSONContent} from "@tiptap/react"
import StarterKit from "@tiptap/starter-kit"
import Highlight from "@tiptap/extension-highlight"
import Typography from "@tiptap/extension-typography"

type Props = {
    content: JSONContent
    onChange: (html: JSONContent) => void
}

export const MarkdownShortcuts: React.FC<Props> = ({ content = "", onChange }) => {
    const editor = useEditor({
        extensions: [
            StarterKit,
            Highlight,
            Typography,
        ],
        content,
        onUpdate({ editor }) {
            const jsonContent = editor.getJSON()
            onChange(jsonContent)
        },
    })

    if (!editor) return null

    return <EditorContent editor={editor} className="tiptap" />
}
