function DisableButton(b)
{
b.disabled = true;
b.value = '投稿中';
b.form.submit();
}